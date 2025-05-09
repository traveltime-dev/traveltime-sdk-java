package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.routes.ArrivalSearch;
import com.traveltime.sdk.dto.requests.routes.DepartureSearch;
import com.traveltime.sdk.dto.responses.RoutesResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoutesRequest extends TravelTimeRequest<RoutesResponse> {
    @NonNull
    @Singular
    List<Location> locations;

    @Valid
    @Singular
    List<DepartureSearch> departureSearches;

    @Valid
    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("routes").build();
        return JsonUtils.toJson(this)
                .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<RoutesResponse> responseType() {
        return RoutesResponse.class;
    }
}
