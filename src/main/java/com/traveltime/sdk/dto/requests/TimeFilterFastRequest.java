package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFastRequest extends TravelTimeRequest<TimeFilterFastResponse> {
    @NonNull
    List<Location> locations;
    @NonNull
    ArrivalSearches arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(String appId, String apiKey, URI uri) {
        String fullUri = uri + "/time-filter/fast";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(fullUri, appId, apiKey, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterFastResponse> responseType() {
        return TimeFilterFastResponse.class;
    }
}
