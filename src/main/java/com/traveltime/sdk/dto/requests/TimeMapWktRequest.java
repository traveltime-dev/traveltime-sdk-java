package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapWktRequest extends TravelTimeRequest<TimeMapWktResponse> {
    List<DepartureSearch> departureSearches;

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
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String uri = baseUri + "time-map";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(credentials, uri, json, acceptType()));
    }

    @Override
    public Class<TimeMapWktResponse> responseType() {
        return TimeMapWktResponse.class;
    }
}
