package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
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
public class TimeMapWktRequest extends TravelTimeRequest<TimeMapWktResponse> {
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    Iterable<Intersection> intersections;

    Iterable<Union> unions;

    @Builder.Default
    boolean withHoles = true;

    public AcceptType acceptType() {
        if (withHoles) {
            return AcceptType.APPLICATION_WKT_JSON;
        } else {
            return AcceptType.APPLICATION_WKT_NO_HOLES_JSON;
        }
    }


    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-map";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), acceptType());
    }

    @Override
    public Class<TimeMapWktResponse> responseType() {
        return TimeMapWktResponse.class;
    }
}
