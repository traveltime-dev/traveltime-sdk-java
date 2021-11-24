package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URI;
import java.util.List;

public abstract class TravelTimeRequest<T> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public abstract Either<TravelTimeError, Request> createRequest(URI baseUri, String appId, String apiKey);

    public abstract Class<T> responseType();

    protected String combineCountries(List<String> withinCountries) {
        if (withinCountries == null || withinCountries.isEmpty())
            return "";
        else
            return "&within.country=" + String.join(",", withinCountries);
    }

    protected Request createGetRequest(
        String url,
        String appId,
        String apiKey
    ) {
        return new Request.Builder()
            .url(url)
            .addHeader("X-Application-Id", appId)
            .addHeader("X-Api-Key", apiKey)
            .get()
            .build();
    }

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
}