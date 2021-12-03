package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.zones.SectorsResult;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterSectorsResponse {
    @NonNull
    List<SectorsResult> results;
}
