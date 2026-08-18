package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.h3.ArrivalSearch;
import com.traveltime.sdk.dto.requests.h3.DepartureSearch;
import com.traveltime.sdk.dto.requests.h3.Property;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.H3Response;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import jakarta.validation.Valid;
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
public class H3Request extends TravelTimeRequest<H3Response> {
    /**
     * H3 cell resolution. Supported values are 4 to 12, and the resolution caps the travel time a
     * search may use.
     *
     * @see <a href="https://docs.traveltime.com/api/reference/h3#limits-of-resolution-and-traveltime">Limits of resolution and travel time</a>
     */
    @NonNull
    Integer resolution;

    @NotEmpty(message = "at least one property must be requested")
    @Singular
    List<Property> properties;

    @Valid
    @Singular
    List<DepartureSearch> departureSearches;

    @Valid
    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Singular
    List<Union> unions;

    @Singular
    List<Intersection> intersections;

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments("h3").build();
        return JsonUtils.toJson(this)
                .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
    }

    @Override
    public Class<H3Response> responseType() {
        return H3Response.class;
    }
}
