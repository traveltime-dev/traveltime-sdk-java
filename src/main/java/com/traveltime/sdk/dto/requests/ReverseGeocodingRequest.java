package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.QueryElement;
import com.traveltime.sdk.utils.Utils;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReverseGeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull Coordinates coordinates;

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val builder = baseUri.newBuilder().addPathSegments("geocoding/reverse");
        val uri = Utils.withQuery(
                builder,
                new QueryElement("lat", coordinates.getLat().toString()),
                new QueryElement("lng", coordinates.getLng().toString())
        ).build();
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}
