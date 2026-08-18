package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.geohash.ArrivalSearches;
import com.traveltime.sdk.dto.requests.geohash.Property;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.GeohashResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
public class GeohashFastRequest extends TravelTimeRequest<GeohashResponse> {
    /**
     * Geohash cell resolution. Caps the travel time a search may use.
     *
     * @see <a href="https://docs.traveltime.com/api/reference/geohash-fast#limits-of-resolution-and-traveltime">Limits of resolution and travel time</a>
     */
    @NonNull
    @Min(value = 4, message = "resolution should be between 4 and 7")
    @Max(value = 7, message = "resolution should be between 4 and 7")
    Integer resolution;

    @NotEmpty(message = "at least one property must be requested")
    @Singular
    List<Property> properties;

    @Valid
    @NonNull
    ArrivalSearches arrivalSearches;

    @Singular
    List<Union> unions;

    @Singular
    List<Intersection> intersections;

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("geohash/fast").build();
        return JsonUtils.toJson(this)
                .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<GeohashResponse> responseType() {
        return GeohashResponse.class;
    }
}
