package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.postcodes.*;
import com.traveltime.sdk.dto.responses.TimeFilterPostcodesResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterPostcodesRequest extends TravelTimeRequest<TimeFilterPostcodesResponse> {
    @Valid
    List<DepartureSearch> departureSearches;
    @Valid
    List<ArrivalSearch> arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(String appId, String apiKey) {
        String uri = "https://api.traveltimeapp.com/v4/time-filter/postcodes";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(uri, appId, apiKey, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterPostcodesResponse> responseType() {
        return TimeFilterPostcodesResponse.class;
    }
}

