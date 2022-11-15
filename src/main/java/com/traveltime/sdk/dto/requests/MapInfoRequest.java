package com.traveltime.sdk.dto.requests;


import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MapInfoRequest extends TravelTimeRequest<MapInfoResponse> {
    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        return Either.right(createGetRequest(baseUri.newBuilder().addPathSegments("map-info").build(), credentials));
    }

    @Override
    public Class<MapInfoResponse> responseType() {
        return MapInfoResponse.class;
    }
}
