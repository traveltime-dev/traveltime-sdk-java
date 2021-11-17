package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class TravelTimeRequest<T> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public abstract Either<TravelTimeError, Request> createRequest(String appId, String apiKey);

    public abstract Class<T> responseType();

    public abstract Boolean isProto();

    protected Request createPostRequest(
        String url,
        String appId,
        String apiKey,
        String jsonString,
        AcceptType acceptType
    ) {
        return new Request.Builder()
            .url(url)
            .addHeader("X-Application-Id", appId)
            .addHeader("X-Api-Key", apiKey)
            .addHeader("Accept", acceptType.getValue())
            .post(RequestBody.create(jsonString, JSON))
            .build();
    }

    protected Request createProtobufRequest(
        String url,
        String username,
        String password,
        byte[] requestBody
    ) {
        return new Request.Builder()
            .url(url)
            .addHeader("Authorization", Credentials.basic(username, password))
            .addHeader("Content-Type", "application/octet-stream")
            .post(RequestBody.create(requestBody))
            .build();
    }
}