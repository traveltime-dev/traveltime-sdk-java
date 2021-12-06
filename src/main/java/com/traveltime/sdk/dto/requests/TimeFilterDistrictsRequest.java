package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.TimeFilterDistrictsResponse;
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
public class TimeFilterDistrictsRequest extends TravelTimeRequest<TimeFilterDistrictsResponse> {
    List<DepartureSearch> departureSearches;
    List<ArrivalSearch> arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String uri = baseUri + "time-filter/postcode-districts";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterDistrictsResponse> responseType() {
        return TimeFilterDistrictsResponse.class;
    }
}