package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.postcodes.*;
import com.traveltime.sdk.dto.responses.TimeFilterPostcodesResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterPostcodesRequest extends TravelTimeRequest<TimeFilterPostcodesResponse> {
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-filter/postcodes";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), AcceptType.APPLICATION_JSON);
    }

    @Override
    public Class<TimeFilterPostcodesResponse> responseType() {
        return TimeFilterPostcodesResponse.class;
    }
}

