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
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapWktRequest extends TravelTimeRequest<TimeMapWktResponse> {
    @Valid
    @Singular
    List<DepartureSearch> departureSearches;
    @Valid
    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Singular
    List<Intersection> intersections;

    @Singular
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
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("time-map").build();
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(credentials, uri, json, acceptType()));
    }

    @Override
    public Class<TimeMapWktResponse> responseType() {
        return TimeMapWktResponse.class;
    }
}
