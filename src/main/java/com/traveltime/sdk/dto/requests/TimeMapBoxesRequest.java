package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapBoxesResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapBoxesRequest extends TravelTimeRequest<TimeMapBoxesResponse> {
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
        AcceptType acceptType = AcceptType.APPLICATION_BOUNDING_BOXES_JSON;
        return JsonUtils.toJson(this).map(json -> createPostRequest(credentials, uri, json, acceptType));
    }

    @Override
    public Class<TimeMapBoxesResponse> responseType() {
        return TimeMapBoxesResponse.class;
    }
}
