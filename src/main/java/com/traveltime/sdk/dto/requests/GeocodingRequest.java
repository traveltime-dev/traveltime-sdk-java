package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.Request;
import org.geojson.FeatureCollection;

import java.net.URI;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull
    String query;

    List<String> withinCountries;

    Integer limit;

    private String getLimit() {
        if (limit == null)
            return "";
        else
            return "&limit=" + limit;
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String uri = baseUri
            + "geocoding/search?query=" + query
            + combineCountries(withinCountries)
            + getLimit();
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}
