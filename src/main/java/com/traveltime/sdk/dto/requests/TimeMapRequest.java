package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.Json;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapRequest extends TravelTimeRequest<TimeMapResponse> {
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI host) throws JsonProcessingException {
        String url = host + "/time-map";
        return createPostRequest(url, appId, apiKey, Json.toJson(this), "application/json");
    }

    @Override
    public Class<TimeMapResponse> responseType() {
        return TimeMapResponse.class;
    }
}
