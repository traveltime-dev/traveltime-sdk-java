package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.HttpResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TravelTimeSDK {
    private final OkHttpClient client = new OkHttpClient();
    @NonNull
    private String appId;
    @NonNull
    private String apiKey;
    @Builder.Default
    private URI uri = URI.create("https://api.traveltimeapp.com/v4");

    public <T> HttpResponse<T> send(final TravelTimeRequest<T> request) {
        try {
            Call call = client.newCall(request.createRequest(appId, apiKey, uri));
            Response response = call.execute();
            if(response.code() == 200) {
                String responseBody = Objects.requireNonNull(response.body()).string();
                T parsedBody = Json.fromJson(responseBody, request.responseType());
                return HttpResponse.<T>builder().httpCode(200).parsedBody(parsedBody).build();
            } else {
                return HttpResponse.<T>builder().httpCode(response.code()).errorMessage(response.message()).build();
            }
        }
        catch (IOException e) {
            return HttpResponse.<T>builder().httpCode(400).errorMessage(e.getMessage()).build();
        }
    }
}
