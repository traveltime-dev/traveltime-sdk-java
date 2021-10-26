package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.Json;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.requests.timefilter.Location;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
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
public class TimeFilterRequest extends TravelTimeRequest<TimeFilterResponse> {
    Iterable<Location> locations;
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI host) throws JsonProcessingException {
        String url = host + "/time-filter";
        return createPostRequest(url, appId, apiKey, Json.toJson(this), "application/json");
    }

    @Override
    public Class<TimeFilterResponse> responseType() {
        return TimeFilterResponse.class;
    }
}
