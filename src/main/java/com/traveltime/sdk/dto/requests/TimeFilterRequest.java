package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.utils.AcceptType;
import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeFilterRequest extends BaseTravelTimePostRequest<TimeFilterResponse> {
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
    protected String endpoint() {
        return "time-filter";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeFilterResponse> responseType() {
        return TimeFilterResponse.class;
    }
}
