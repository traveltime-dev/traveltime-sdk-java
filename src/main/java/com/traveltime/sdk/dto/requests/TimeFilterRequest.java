package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeFilterRequest extends TravelTimeRequest<TimeFilterResponse> {
    @NonNull
    Iterable<Location> locations;
    @Valid
    Iterable<DepartureSearch> departureSearches;
    @Valid
    Iterable<ArrivalSearch> arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-filter";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), AcceptType.APPLICATION_JSON);
    }

    @Override
    public Class<TimeFilterResponse> responseType() {
        return TimeFilterResponse.class;
    }
}
