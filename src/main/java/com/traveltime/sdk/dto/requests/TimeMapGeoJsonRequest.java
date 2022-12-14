package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapGeoJsonResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
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
public class TimeMapGeoJsonRequest extends TravelTimeRequest<TimeMapGeoJsonResponse> {
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

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("time-map").build();
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_GEO_JSON));
    }

    @Override
    public Class<TimeMapGeoJsonResponse> responseType() {
        return TimeMapGeoJsonResponse.class;
    }
}