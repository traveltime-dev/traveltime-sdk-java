package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.QueryElement;
import com.traveltime.sdk.utils.Version;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;

@SuperBuilder
@NoArgsConstructor
public abstract class TravelTimeRequest<T> {
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public abstract Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials);

    public abstract Class<T> responseType();

    protected QueryElement combineCountries(List<String> withinCountries) {
        if (withinCountries == null) withinCountries = new ArrayList<>();
        return new QueryElement("within.country", String.join(",", withinCountries));
    }

    protected Request createGetRequest(HttpUrl url, TravelTimeCredentials credentials) {
        return new Request.Builder()
                .url(url)
                .headers(credentials.getHeaders())
                .addHeader("User-Agent", "Travel Time Java SDK " + Version.getVersion())
                .get()
                .build();
    }
}
