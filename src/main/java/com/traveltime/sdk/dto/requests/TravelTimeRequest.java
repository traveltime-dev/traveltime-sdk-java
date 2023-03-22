package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.QueryElement;
import com.traveltime.sdk.utils.Version;
import io.vavr.control.Either;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.List;

public abstract class TravelTimeRequest<T> {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public abstract Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials);

    public abstract Class<T> responseType();

    protected QueryElement combineCountries(List<String> withinCountries) {
        if(withinCountries == null) withinCountries = new ArrayList<>();
        return new QueryElement("within.country", String.join(",", withinCountries));
    }

    protected Request createGetRequest(
        HttpUrl url,
        TravelTimeCredentials credentials
    ) {
        return new Request.Builder()
            .url(url)
            .headers(credentials.getHeaders())
            .addHeader("User-Agent", "Travel Time Java SDK " + Version.getVersion())
            .get()
            .build();
    }

    protected Request createPostRequest(
        TravelTimeCredentials credentials,
        HttpUrl url,
        String jsonString,
        AcceptType acceptType
    ) {
        return new Request.Builder()
            .url(url)
            .headers(credentials.getHeaders())
            .addHeader("Accept", acceptType.getValue())
            .addHeader("User-Agent", "Travel Time Java SDK " + Version.getVersion())
            .post(RequestBody.create(jsonString, JSON))
            .build();
    }
}