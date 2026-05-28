package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.*;
import io.vavr.control.Either;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.val;
import okhttp3.*;

@SuperBuilder
@NoArgsConstructor
public abstract class BaseTravelTimePostRequest<T> extends TravelTimeRequest<T> {

    protected abstract String endpoint();

    protected abstract AcceptType acceptType();

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments(endpoint()).build();
        return JsonUtils.toJson(this).map(json -> createPostRequest(credentials, uri, json));
    }

    protected final Request createPostRequest(TravelTimeCredentials credentials, HttpUrl url, String jsonString) {
        return new Request.Builder()
                .url(url)
                .headers(credentials.getHeaders())
                .addHeader("Accept", acceptType().getValue())
                .addHeader("User-Agent", TravelTimeSDK.fullName())
                .post(RequestBody.create(jsonString, JSON))
                .build();
    }
}
