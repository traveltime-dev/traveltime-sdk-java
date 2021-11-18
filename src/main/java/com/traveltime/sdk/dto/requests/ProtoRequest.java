package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URI;

public abstract class ProtoRequest<T> {

    public abstract Either<TravelTimeError, Request> createRequest(URI baseUri, String appId, String apiKey);

    public abstract Either<TravelTimeError, T> parseBytes(byte[] body);

    protected Request createProtobufRequest(
        String url,
        String username,
        String password,
        byte[] requestBody
    ) {
        return new Request.Builder()
            .url(url)
            .addHeader("Authorization", Credentials.basic(username, password))
            .addHeader("Content-Type", AcceptType.APPLICATION_OCTET_STREAM.getValue())
            .post(RequestBody.create(requestBody))
            .build();
    }
}
