package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.requests.zones.ArrivalSearch;
import com.traveltime.sdk.dto.requests.zones.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeFilterDistrictsResponse;
import com.traveltime.sdk.utils.AcceptType;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeFilterDistrictsRequest extends BaseTravelTimePostRequest<TimeFilterDistrictsResponse> {
    @Singular
    List<DepartureSearch> departureSearches;

    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Override
    protected String endpoint() {
        return "time-filter/postcode-districts";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeFilterDistrictsResponse> responseType() {
        return TimeFilterDistrictsResponse.class;
    }
}
