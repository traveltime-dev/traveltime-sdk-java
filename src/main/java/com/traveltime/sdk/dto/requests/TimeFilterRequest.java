package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
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
public class TimeFilterRequest extends TravelTimeRequest<TimeFilterResponse> {
  @NonNull @Singular List<Location> locations;
  @Valid @Singular List<DepartureSearch> departureSearches;
  @Valid @Singular List<ArrivalSearch> arrivalSearches;

  @Override
  public Either<TravelTimeError, Request> createRequest(
      HttpUrl baseUri, TravelTimeCredentials credentials) {
    val uri = baseUri.newBuilder().addPathSegments("time-filter").build();
    return JsonUtils.toJson(this)
        .map(json -> createPostRequest(credentials, uri, json, AcceptType.APPLICATION_JSON));
  }

  @Override
  public Class<TimeFilterResponse> responseType() {
    return TimeFilterResponse.class;
  }
}
