package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.AcceptType;
import com.traveltime.sdk.JsonUtils;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.TimeFilterSectorsResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterSectorsRequest extends TravelTimeRequest<TimeFilterSectorsResponse> {
    List<DepartureSearch> departureSearches;
    List<ArrivalSearch> arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(String appId, String apiKey, URI uri) {
        String fullUri = uri + "/time-filter/postcode-sectors";
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(fullUri, appId, apiKey, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterSectorsResponse> responseType() {
        return TimeFilterSectorsResponse.class;
    }
}