package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.utils.AcceptType;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastRequest extends BaseTravelTimePostRequest<TimeFilterFastResponse> {
    @NonNull
    @Singular
    List<Location> locations;

    @NonNull
    ArrivalSearches arrivalSearches;

    @Override
    protected String endpoint() {
        return "time-filter/fast";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeFilterFastResponse> responseType() {
        return TimeFilterFastResponse.class;
    }
}
