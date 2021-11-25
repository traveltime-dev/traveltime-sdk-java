package com.traveltime.sdk.dto.requests;


import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import okhttp3.Request;

import java.net.URI;

@AllArgsConstructor
public class MapInfoRequest extends TravelTimeRequest<MapInfoResponse> {
    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, String appId, String apiKey) {
        return Either.right(createGetRequest(baseUri + "map-info", appId, apiKey));
    }

    @Override
    public Class<MapInfoResponse> responseType() {
        return MapInfoResponse.class;
    }
}
