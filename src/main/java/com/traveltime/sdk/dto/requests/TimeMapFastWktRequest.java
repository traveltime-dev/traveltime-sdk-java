package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.timemapfast.ArrivalSearches;
import com.traveltime.sdk.dto.responses.TimeMapFastWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapFastWktRequest extends TravelTimeRequest<TimeMapFastWktResponse> {
    @NonNull
    ArrivalSearches arrivalSearches;
    @Builder.Default
    boolean withHoles = true;

    private AcceptType acceptType() {
        return withHoles ? AcceptType.APPLICATION_WKT_JSON : AcceptType.APPLICATION_WKT_NO_HOLES_JSON;
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("time-map/fast").build();
        return JsonUtils
                .toJson(this)
                .map(json -> createPostRequest(credentials, uri, json, acceptType()));
    }

    @Override
    public Class<TimeMapFastWktResponse> responseType() {
        return TimeMapFastWktResponse.class;
    }
}
