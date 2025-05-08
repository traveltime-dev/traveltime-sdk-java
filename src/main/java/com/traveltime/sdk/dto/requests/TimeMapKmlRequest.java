package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.utils.JsonUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;
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
public class TimeMapKmlRequest extends TravelTimeRequest<Kml> {
  @Valid @Singular List<DepartureSearch> departureSearches;
  @Valid @Singular List<ArrivalSearch> arrivalSearches;

  @Singular List<Intersection> intersections;

  @Singular List<Union> unions;

  @Override
  public Either<TravelTimeError, Request> createRequest(
      HttpUrl baseUri, TravelTimeCredentials credentials) {
    val uri = baseUri.newBuilder().addPathSegments("time-map").build();
    return JsonUtils.toJson(this)
        .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_KML));
  }

  @Override
  public Class<Kml> responseType() {
    return Kml.class;
  }
}
