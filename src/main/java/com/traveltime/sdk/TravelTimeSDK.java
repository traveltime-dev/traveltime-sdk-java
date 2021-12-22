package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.validation.*;
import lombok.*;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelTimeSDK {
    private final OkHttpClient client = new OkHttpClient();
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private static final String IO_CONNECTION_ERROR = "Something went wrong when connecting to the Traveltime API: ";

    @NonNull
    private final TravelTimeCredentials credentials;

    @Builder.Default
    private URI baseUri = URI.create("https://api.traveltimeapp.com/v4/");

    @Builder.Default
    private URI baseProtoUri = URI.create("https://proto.api.traveltimeapp.com/api/v2/");

    private <T> Option<ValidationError> validate(TravelTimeRequest<T> request) {
        Set<ConstraintViolation<TravelTimeRequest<T>>> violations = validator.validate(request);
        if(!violations.isEmpty()) {
            String msg = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(". "));

            return Option.of(new ValidationError(msg));
        }

        return Option.none();
    }

    private <T> Either<TravelTimeError, T> parseJsonBody(TravelTimeRequest<T> request, int responseCode, String body) {
        if(responseCode == 200) {
            return JsonUtils.fromJson(body, request.responseType());
        } else {
            return JsonUtils
                .fromJson(body, ResponseError.class)
                .flatMap(responseError -> Either.left(responseError));
        }
    }

    private <T> Either<TravelTimeError, T> parseByteResponse(ProtoRequest<T> request, Response response) {
        Either<TravelTimeError, T> protoResponse = Try
            .of(() -> Objects.requireNonNull(response.body()).bytes())
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()))
            .flatMap(request::parseBytes);


        response.close();
        return protoResponse;
    }

    private <T> Either<TravelTimeError, T> getHttpResponse(TravelTimeRequest<T> request, Response response) {
        Either<TravelTimeError, T> httpResponse = Try
            .of(() -> Objects.requireNonNull(response.body()).string())
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()))
            .flatMap(body -> parseJsonBody(request, response.code(), body));

        response.close();
        return httpResponse;
    }

    private Either<TravelTimeError, Response> executeRequest(Request request) {
        return Try
            .of(() -> client.newCall(request).execute())
            .toEither()
            .mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()));
    }

    public <T> Either<TravelTimeError, T> sendProto(ProtoRequest<T> request) {
        return request
            .createRequest(baseProtoUri, credentials)
            .flatMap(this::executeRequest)
            .flatMap(response -> parseByteResponse(request, response));
    }

    public <T> Either<TravelTimeError, T> send(TravelTimeRequest<T> request) {
        Option<ValidationError> validationError = validate(request);
        if(validationError.isDefined()) {
            return Either.left(validationError.get());
        } else {
            return request
                .createRequest(baseUri, credentials)
                .flatMap(this::executeRequest)
                .flatMap(response -> getHttpResponse(request, response));
        }
    }

    private <T> void completeFuture(
        CompletableFuture<Either<TravelTimeError, T>> future,
        TravelTimeRequest<T> travelTimeRequest,
        Request request
    ) {
        client
            .newCall(request)
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    future.complete(Either.left(new IOError(e, IO_CONNECTION_ERROR + e.getMessage())));
                }

                @Override
                public void onResponse(Call call, Response response) {
                    future.complete(getHttpResponse(travelTimeRequest, response));
                }
           });
    }

    public <T> CompletableFuture<Either<TravelTimeError, T>> sendAsync(TravelTimeRequest<T> request) {
        final CompletableFuture<Either<TravelTimeError, T>> future = new CompletableFuture<>();
        Option<ValidationError> validationError = validate(request);

        if(validationError.isDefined()) {
            future.complete(Either.left(validationError.get()));
        } else {
            request
                .createRequest(baseUri, credentials)
                .peekLeft(error -> future.complete(Either.left(error)))
                .peek(createdRequest -> completeFuture(future, request, createdRequest));
        }

        return future;
    }
}
