package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.QueryElement;
import com.traveltime.sdk.utils.Utils;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReverseGeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull
    Coordinates coordinates;

    List<String> withinCountries;

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String query = Utils.composeQuery(
                new QueryElement("lat", coordinates.getLat().toString()),
                new QueryElement("lng", coordinates.getLng().toString()),
                combineCountries(withinCountries)
        );
        String uri = Utils.withQuery(baseUri.resolve("/geocoding/reverse"), query).toString();
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}
