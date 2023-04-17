package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.List;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastRequest extends TravelTimeRequest<TimeFilterFastResponse> {
    @NonNull
    @Singular
    List<Location> locations;
    @NonNull
    ArrivalSearches arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("time-filter/fast").build();
        return JsonUtils
            .toJson(this)
            .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<TimeFilterFastResponse> responseType() {
        return TimeFilterFastResponse.class;
    }
}
