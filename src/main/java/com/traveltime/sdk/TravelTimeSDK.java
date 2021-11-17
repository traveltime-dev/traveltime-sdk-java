package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
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

    @NonNull
    private String appId;
    @NonNull
    private String apiKey;
    @Builder.Default
    private URI uri = URI.create("https://api.traveltimeapp.com/v4");

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

    private <T> Either<TravelTimeError, T> getHttpResponse(TravelTimeRequest<T> request, Response response)  {
        return Try
            .of(() -> Objects.requireNonNull(response.body()).string())
            .toEither()
            .<TravelTimeError>mapLeft(IOError::new)
            .flatMap(body -> parseJsonBody(request, response.code(), body));
    }

    private Either<TravelTimeError, Response> executeRequest(Request request) {
        return Try
            .of(() -> client.newCall(request).execute())
            .toEither()
            .mapLeft(IOError::new);
    }

    public <T> Either<TravelTimeError, T> send(TravelTimeRequest<T> request) {
        Option<ValidationError> validationError = validate(request);
        if(validationError.isDefined()) {
            return Either.left(validationError.get());
        } else {
            return request
                .createRequest(appId, apiKey, uri)
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
                    future.complete(Either.left(new IOError(e)));
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
                .createRequest(appId, apiKey, uri)
                .peekLeft(error -> future.complete(Either.left(error)))
                .peek(createdRequest -> completeFuture(future, request, createdRequest));
        }

        return future;
    }
}
