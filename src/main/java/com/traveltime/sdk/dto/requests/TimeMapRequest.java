package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.Json;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import okhttp3.Request;

import java.net.URI;


@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TimeMapRequest extends TravelTimeRequest<TimeMapResponse> {
    Iterable<DepartureSearch> departureSearches;
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
