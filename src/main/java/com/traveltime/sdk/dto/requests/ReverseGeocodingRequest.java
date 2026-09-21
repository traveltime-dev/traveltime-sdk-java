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

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReverseGeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull
    Coordinates coordinates;

    /**
     * BCP47 language tag for the Accept-Language header, controlling the language of the
     * returned place names.
     */
    String acceptLanguage;

    public ReverseGeocodingRequest(@NonNull Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val builder = baseUri.newBuilder().addPathSegments("geocoding/reverse");
        val uri = Utils.withQuery(
                        builder,
                        new QueryElement("lat", coordinates.getLat().toString()),
                        new QueryElement("lng", coordinates.getLng().toString()))
                .build();
        Request request = createGetRequest(uri, credentials);
        if (acceptLanguage != null) {
            request = request.newBuilder()
                    .header("Accept-Language", acceptLanguage)
                    .build();
        }
        return Either.right(request);
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}
