package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.TimeFilterSectorsResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterSectorsRequest extends TravelTimeRequest<TimeFilterSectorsResponse> {
    Iterable<DepartureSearch> departureSearches;
    Iterable<ArrivalSearch> arrivalSearches;

    @Override
    public Request createRequest(String appId, String apiKey, URI uri) throws JsonProcessingException {
        String fullUri = uri + "/time-filter/postcode-sectors";
        return createPostRequest(fullUri, appId, apiKey, JsonUtils.toJson(this), AcceptType.APPLICATION_JSON);
    }

    @Override
    public Class<TimeFilterSectorsResponse> responseType() {
        return TimeFilterSectorsResponse.class;
    }
}