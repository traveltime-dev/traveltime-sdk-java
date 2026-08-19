package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.requests.zones.ArrivalSearch;
import com.traveltime.sdk.dto.requests.zones.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeFilterSectorsResponse;
import com.traveltime.sdk.utils.AcceptType;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeFilterSectorsRequest extends BaseTravelTimePostRequest<TimeFilterSectorsResponse> {

    @Singular
    List<DepartureSearch> departureSearches;

    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Override
    protected String endpoint() {
        return "time-filter/postcode-sectors";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeFilterSectorsResponse> responseType() {
        return TimeFilterSectorsResponse.class;
    }
}
