package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URI;

public abstract class ProtoRequest<T> {

    public abstract Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials);

    public abstract Either<TravelTimeError, T> parseBytes(byte[] body);

    protected Request createProtobufRequest(
        TravelTimeCredentials credentials,
        String url,
        byte[] requestBody
    ) {
        return new Request.Builder()
            .url(url)
            .headers(credentials.getHeaders())
            .addHeader("Content-Type", AcceptType.APPLICATION_OCTET_STREAM.getValue())
            .post(RequestBody.create(requestBody))
            .build();
    }
}
