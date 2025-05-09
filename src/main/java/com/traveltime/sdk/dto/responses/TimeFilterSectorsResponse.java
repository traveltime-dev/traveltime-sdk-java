package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.zones.SectorsResult;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterSectorsResponse {
    @NonNull
    List<SectorsResult> results;
}
