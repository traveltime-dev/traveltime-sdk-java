package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.requests.postcodes.ArrivalSearch;
import com.traveltime.sdk.dto.requests.postcodes.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeFilterPostcodesResponse;
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
public class TimeFilterPostcodesRequest extends BaseTravelTimePostRequest<TimeFilterPostcodesResponse> {
    @Valid
    @Singular
    List<DepartureSearch> departureSearches;

    @Valid
    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Override
    protected String endpoint() {
        return "time-filter/postcodes";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeFilterPostcodesResponse> responseType() {
        return TimeFilterPostcodesResponse.class;
    }
}
