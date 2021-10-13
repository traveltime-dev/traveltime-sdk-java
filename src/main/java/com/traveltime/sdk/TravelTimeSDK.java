package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import okhttp3.*;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class TravelTimeSDK {
    private final OkHttpClient client;
    private final String appId;
    private final String apiKey;

    private final URI BASE_URI = URI.create("https://api.traveltimeapp.com/v4");

    public TravelTimeSDK(final OkHttpClient client, final String appId, final String apiKey) {
        this.client = client;
        this.appId = appId;
        this.apiKey = apiKey;
    }

    public <T> T send(final TravelTimeRequest<T> request) throws IOException {
        Call call = client.newCall(request.createRequest(appId, apiKey, BASE_URI));
        Response response = call.execute();
        String responseBody = Objects.requireNonNull(response.body()).string();

        return Json.fromJson(responseBody, request.responseType());
    }
}
