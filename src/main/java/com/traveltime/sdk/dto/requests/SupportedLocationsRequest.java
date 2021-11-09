package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;


@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class SupportedLocationsRequest extends TravelTimeRequest<SupportedLocationsResponse> {
    @NonNull
    Iterable<Location> locations;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/supported-locations";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), AcceptType.APPLICATION_JSON);
    }

    @Override
    public Class<SupportedLocationsResponse> responseType() {
        return SupportedLocationsResponse.class;
    }
}
