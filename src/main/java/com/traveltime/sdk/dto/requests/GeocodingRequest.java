package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.Request;
import org.geojson.FeatureCollection;

import java.net.URI;
import java.util.List;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class GeocodingRequest extends TravelTimeRequest<FeatureCollection> {
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
    public Either<TravelTimeError, Request> createRequest(URI baseUri, String appId, String apiKey) {
        String uri = baseUri
            + "geocoding/search?query=" + query
            + combineCountries(withinCountries)
            + getLimit();
        return Either.right(createGetRequest(uri, appId, apiKey));
    }

    @Override
    public Class<FeatureCollection> responseType() {
        return FeatureCollection.class;
    }
}
