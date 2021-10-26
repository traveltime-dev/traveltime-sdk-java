package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.HttpResponse;
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

    private <T> Optional<String> validate(TravelTimeRequest<T> request) {
        Set<ConstraintViolation<TravelTimeRequest<T>>> violations = validator.validate(request);
        return violations.stream().map(ConstraintViolation::getMessage).findFirst();
    }

    private <T> HttpResponse<T> getHttpResponse(TravelTimeRequest<T> request, Response response) throws IOException {
        if(response.code() == 200) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            T parsedBody = Json.fromJson(responseBody, request.responseType());
            return HttpResponse.<T>builder().httpCode(200).parsedBody(parsedBody).build();
        } else {
            return HttpResponse.<T>builder().httpCode(response.code()).errorMessage(response.message()).build();
        }
    }

    public <T> HttpResponse<T> send(TravelTimeRequest<T> request) {
        Optional<String> validation = validate(request);
        if(validation.isPresent()) {
            return HttpResponse.<T>builder().httpCode(422).errorMessage(validation.get()).build();
        }

        try {
            Call call = client.newCall(request.createRequest(appId, apiKey, uri));
            Response response = call.execute();
            return getHttpResponse(request, response);
        }
        catch (IOException e) {
            return HttpResponse.<T>builder().httpCode(400).errorMessage(e.getMessage()).build();
        }
    }

    public <T> CompletableFuture<HttpResponse<T>> sendAsync(TravelTimeRequest<T> request) {
        final CompletableFuture<HttpResponse<T>> future = new CompletableFuture<>();

        Optional<String> validation = validate(request);
        if(validation.isPresent()) {
            HttpResponse<T> response = HttpResponse.<T>builder().httpCode(422).errorMessage(validation.get()).build();
            future.complete(response);
            return future;
        }

        try {
            client
                .newCall(request.createRequest(appId, apiKey, uri))
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        HttpResponse<T> response = HttpResponse
                                .<T>builder()
                                .httpCode(400)
                                .errorMessage(e.getMessage())
                                .build();

                        future.complete(response);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        future.complete(getHttpResponse(request, response));
                    }
                }
            );

        }
        catch (IOException e) {
            HttpResponse<T> response = HttpResponse.<T>builder().httpCode(400).errorMessage(e.getMessage()).build();
            future.complete(response);
        }
        return future;
    }

}
