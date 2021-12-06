package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
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
public class ReverseGeocodingRequest extends TravelTimeRequest<FeatureCollection> {
    @NonNull
    Coordinates coordinates;

    List<String> withinCountries;

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String uri = baseUri
            + "geocoding/reverse?lat=" + coordinates.getLat()
            + "&lng=" + coordinates.getLng()
            + combineCountries(withinCountries);
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<FeatureCollection> responseType() {
        return FeatureCollection.class;
    }
}
