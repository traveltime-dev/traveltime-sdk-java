package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapBoxesResponse;
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
public class TimeMapBoxesRequest  extends TravelTimeRequest<TimeMapBoxesResponse> {
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    Iterable<Intersection> intersections;

    Iterable<Union> unions;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-map";
        AcceptType acceptType = AcceptType.APPLICATION_BOUNDING_BOXES_JSON;
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), acceptType);
    }

    @Override
    public Class<TimeMapBoxesResponse> responseType() {
        return TimeMapBoxesResponse.class;
    }
}
