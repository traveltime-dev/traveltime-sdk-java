package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFastRequest extends TravelTimeRequest<TimeFilterFastResponse> {
    @NonNull
    Iterable<Location> locations;
    @NonNull
    ArrivalSearches arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-filter/fast";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), AcceptType.APPLICATION_JSON);
    }

    @Override
    public Class<TimeFilterFastResponse> responseType() {
        return TimeFilterFastResponse.class;
    }
}
