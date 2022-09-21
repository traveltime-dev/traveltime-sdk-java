package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.*;
import com.traveltime.sdk.utils.JsonUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import jakarta.validation.*;
import lombok.*;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelTimeSDK {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private static final String IO_CONNECTION_ERROR = "Something went wrong when connecting to the Traveltime API: ";
    private static final int DEFAULT_BATCH_COUNT = 4;
    private static final int MINIMUM_SPLIT_SIZE =  10_000;

    @Builder.Default
    private OkHttpClient client = new OkHttpClient();

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
            if(request.responseType() == Kml.class) {
                return Try
                    .of(() -> (T)Kml.unmarshal(body))
                    .toEither()
                    .mapLeft(XmlError::new);
            }
            else {
                return JsonUtils.fromJson(body, request.responseType());
            }
        } else {
            return JsonUtils
                .fromJson(body, ResponseError.class)
                .flatMap(responseError -> Either.left(responseError));
        }
    }

    private <T> Either<TravelTimeError, T> deserializeResponse(ProtoRequest<T> request, Response response) {
        Either<TravelTimeError, T> protoResponse = Try
            .of(() -> Objects.requireNonNull(response.body()).bytes())
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()))
            .flatMap(request::parseBytes);

        response.close();
        return protoResponse;
    }

    private <T> Either<TravelTimeError, T> getParsedResponse(TravelTimeRequest<T> request, Response response) {
        val httpResponse = Try
            .of(() -> Objects.requireNonNull(response.body()).string())
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()))
            .flatMap(body -> parseJsonBody(request, response.code(), body));

        response.close();
        return httpResponse;
    }

    private Either<TravelTimeError, String> getUnparsedResponse(Response response) {
       val httpResponse = Try
            .of(() -> Objects.requireNonNull(response.body()).string())
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()));

        response.close();
        return httpResponse;
    }

    private Either<TravelTimeError, Response> executeRequest(Request request) {
        return Try
            .of(() -> client.newCall(request).execute())
            .toEither()
            .mapLeft(cause -> new IOError(cause, IO_CONNECTION_ERROR + cause.getMessage()));
    }

    public <T> CompletableFuture<Either<TravelTimeError, T>> sendProtoAsync(ProtoRequest<T> request) {
        return CompletableFuture
            .supplyAsync(() -> request.createRequest(baseProtoUri, credentials), client.dispatcher().executorService())
            .thenCompose(req -> {
                final CompletableFuture<Either<TravelTimeError, T>> future = new CompletableFuture<>();

                req.peekLeft(error -> future.complete(Either.left(error)))
                    .peek(createdRequest -> completeProtoFuture(future, request, createdRequest));

                return future;
            });
    }

    public <T> Either<TravelTimeError, T> sendProto(ProtoRequest<T> request) {
        return request
            .createRequest(baseProtoUri, credentials)
            .flatMap(this::executeRequest)
            .flatMap(response -> deserializeResponse(request, response));
    }

    private static <T> Either<TravelTimeError, T> getFuture(CompletableFuture<Either<TravelTimeError, T>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return Either.left(new IOError(e, "Error while executing an async request."));
        }
    }

    public Either<TravelTimeError, TimeFilterFastProtoResponse> sendProtoBatched(
        TimeFilterFastProtoRequest request
    ) {
        return sendProtoBatchedCount(request, DEFAULT_BATCH_COUNT);
    }

    public Either<TravelTimeError, TimeFilterFastProtoResponse> sendProtoBatched(
        TimeFilterFastProtoRequest request,
        int batchSizeHint
    ) {
        return getFuture(sendProtoAsyncBatched(request, batchSizeHint));
    }

    public Either<TravelTimeError, TimeFilterFastProtoResponse> sendProtoBatchedCount(
        TimeFilterFastProtoRequest request,
        int batchCount
    ) {
        int requestSize = request.getOneToMany().getDestinationCoordinates().size();
        return getFuture(sendProtoAsyncBatched(request, Math.max(MINIMUM_SPLIT_SIZE, requestSize / batchCount)));
    }

    public CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>> sendProtoAsyncBatched(
        TimeFilterFastProtoRequest request
    ) {
        return sendProtoAsyncBatchedCount(request, DEFAULT_BATCH_COUNT);
    }

    public CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>> sendProtoAsyncBatchedCount(
        TimeFilterFastProtoRequest request,
        int batchCount
    ) {
        int requestSize = request.getOneToMany().getDestinationCoordinates().size();
        return sendProtoAsyncBatched(request, Math.max(MINIMUM_SPLIT_SIZE, requestSize / batchCount));
    }

    public CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>> sendProtoAsyncBatched(
        TimeFilterFastProtoRequest request,
        int batchSizeHint
    ) {
        val splitRequests = TimeFilterFastProtoRequest.split(request, batchSizeHint);
        val futures = new ArrayList<CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>>>(splitRequests.size());
        val results = new ArrayList<TimeFilterFastProtoResponse>(splitRequests.size());

        for(val req : splitRequests) {
            futures.add(sendProtoAsync(req));
        }

        return CompletableFuture
            .allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(it -> {
                    for (val future : futures) {
                        val part = getFuture(future);
                        if (part.isLeft()) {
                            return part;
                        } else {
                            results.add(part.get());
                        }
                    }
                    return Either.right(TimeFilterFastProtoResponse.merge(results));
                }
            );
    }

    public <T> Either<TravelTimeError, T> send(TravelTimeRequest<T> request) {
        Option<ValidationError> validationError = validate(request);
        if(validationError.isDefined()) {
            return Either.left(validationError.get());
        } else {
            return request
                .createRequest(baseUri, credentials)
                .flatMap(this::executeRequest)
                .flatMap(response -> getParsedResponse(request, response));
        }
    }

    public <T> Either<TravelTimeError, String> getJsonResponse(TravelTimeRequest<T> request) {
        Option<ValidationError> validationError = validate(request);
        if(validationError.isDefined()) {
            return Either.left(validationError.get());
        } else {
            return request
                .createRequest(baseUri, credentials)
                .flatMap(this::executeRequest)
                .flatMap(this::getUnparsedResponse);
        }
    }

    private <T> void completeProtoFuture(
        CompletableFuture<Either<TravelTimeError, T>> future,
        ProtoRequest<T> protoRequest,
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
                    future.complete(deserializeResponse(protoRequest, response));
                }
            });
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
                    future.complete(getParsedResponse(travelTimeRequest, response));
                }
           });
    }

    public <T> CompletableFuture<Either<TravelTimeError, T>> sendAsync(TravelTimeRequest<T> request) {
        return CompletableFuture
            .supplyAsync(() -> validate(request))
            .thenCompose(validationError -> {
                if (validationError.isDefined()) {
                    return CompletableFuture.completedFuture(Either.left(validationError.get()));
                }
                return CompletableFuture
                    .supplyAsync(() -> request.createRequest(baseUri, credentials))
                    .thenCompose(req -> {
                        CompletableFuture<Either<TravelTimeError, T>> future = new CompletableFuture<>();

                        req.peekLeft(error -> future.complete(Either.left(error)))
                           .peek(createdRequest -> completeFuture(future, request, createdRequest));

                        return future;
                    });
                }
            );
    }

    /** Only useful for applications that need to aggressively clean up resources, as this is done automatically by OkHttp. */
    public void close() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}
