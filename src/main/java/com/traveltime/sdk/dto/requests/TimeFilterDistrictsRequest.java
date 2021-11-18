package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.TimeFilterDistrictsResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
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
public class TimeFilterDistrictsRequest extends TravelTimeRequest<TimeFilterDistrictsResponse> {
    List<DepartureSearch> departureSearches;
    List<ArrivalSearch> arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(String appId, String apiKey) {
        String uri = "https://api.traveltimeapp.com/v4/time-filter/postcode-districts";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(uri, appId, apiKey, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterDistrictsResponse> responseType() {
        return TimeFilterDistrictsResponse.class;
    }
}