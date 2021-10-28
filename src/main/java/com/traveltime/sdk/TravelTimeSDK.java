package com.traveltime.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.HttpResponse;
import com.traveltime.sdk.exceptions.RequestValidationException;
import jakarta.validation.*;
import lombok.*;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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

    private <T> void validate(TravelTimeRequest<T> request) throws RequestValidationException {
        Set<ConstraintViolation<TravelTimeRequest<T>>> violations = validator.validate(request);
        Optional<String> msg = violations.stream().map(ConstraintViolation::getMessage).findFirst();
        if(msg.isPresent()) {
            throw new RequestValidationException(msg.get());
        }
    }

    private <T> HttpResponse<T> getHttpResponse(TravelTimeRequest<T> request, Response response) throws IOException {
        if(response.code() == 200) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            T parsedBody = JsonUtils.fromJson(responseBody, request.responseType());
            return HttpResponse.<T>builder().httpCode(200).parsedBody(parsedBody).build();
        } else {
            return HttpResponse.<T>builder().httpCode(response.code()).errorMessage(response.message()).build();
        }
    }

    public <T> HttpResponse<T> send(TravelTimeRequest<T> request)
            throws RequestValidationException, IOException {
        validate(request);
        Call call = client.newCall(request.createRequest(appId, apiKey, uri));
        Response response = call.execute();
        return getHttpResponse(request, response);
    }

    public <T> CompletableFuture<HttpResponse<T>> sendAsync(TravelTimeRequest<T> request)
            throws RequestValidationException, IOException {
        validate(request);

        final CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();
        client
            .newCall(request.createRequest(appId, apiKey, uri))
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    future.completeExceptionally(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    future.complete(getHttpResponse(request, response));
                }
            });

        return future;
    }

}
