package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.errors.*;
import com.traveltime.sdk.utils.JsonUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.*;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelTimeSDK {
    private final OkHttpClient client = new OkHttpClient();
    private static final String IO_CONNECTION_ERROR = "Something went wrong when connecting to the Traveltime API: ";
    private static final int DEFAULT_BATCH_COUNT = 4;
    private static final int MINIMUM_SPLIT_SIZE =  10_000;

    @NonNull
    private final TravelTimeCredentials credentials;

    @Builder.Default
    private URI baseUri = URI.create("https://api.traveltimeapp.com/v4/");

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

    private static <T> Either<TravelTimeError, T> getFuture(CompletableFuture<Either<TravelTimeError, T>> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return Either.left(new IOError(e, "Error while executing an async request."));
        }
    }

    public <T> Either<TravelTimeError, T> send(TravelTimeRequest<T> request) {
        return request
            .createRequest(baseUri, credentials)
            .flatMap(this::executeRequest)
            .flatMap(response -> getHttpResponse(request, response));

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
        return CompletableFuture
            .supplyAsync(() -> request.createRequest(baseUri, credentials))
            .thenCompose(req -> {
                CompletableFuture<Either<TravelTimeError, T>> future = new CompletableFuture<>();
                req
                    .peekLeft(error -> future.complete(Either.left(error)))
                    .peek(createdRequest -> completeFuture(future, request, createdRequest));

                return future;
            });
    }

    /** Only useful for applications that need to aggressively clean up resources, as this is done automatically by OkHttp. */
    public void close() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }
}
