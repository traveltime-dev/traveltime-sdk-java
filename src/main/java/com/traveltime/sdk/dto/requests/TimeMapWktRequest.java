package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapWktRequest extends TravelTimeRequest<TimeMapWktResponse> {
    @Valid
    List<DepartureSearch> departureSearches;
    @Valid
    List<ArrivalSearch> arrivalSearches;

    List<Intersection> intersections;

    List<Union> unions;

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
    public Either<TravelTimeError, Request> createRequest(String appId, String apiKey, URI uri) {
        String fullUri = uri + "/time-map";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(fullUri, appId, apiKey, json, acceptType()));
    }

    @Override
    public Class<TimeMapWktResponse> responseType() {
        return TimeMapWktResponse.class;
    }
}
