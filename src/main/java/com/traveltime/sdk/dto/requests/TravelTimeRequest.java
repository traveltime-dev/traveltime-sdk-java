package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URI;

public abstract class TravelTimeRequest<T> {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public abstract Request createRequest(String appId, String apiKey, URI host) throws JsonProcessingException;

    public abstract Class<T> responseType();

    protected Request createPostRequest(String url, String appId, String apiKey, String jsonString, String acceptType) { // String url and acceptType are temporary hacks
        return new Request.Builder()
            .url(url)
            .addHeader("X-Application-Id", appId)
            .addHeader("X-Api-Key", apiKey)
            .addHeader("Accept", acceptType)
            .post(RequestBody.create(jsonString, JSON))
            .build();
    }
}