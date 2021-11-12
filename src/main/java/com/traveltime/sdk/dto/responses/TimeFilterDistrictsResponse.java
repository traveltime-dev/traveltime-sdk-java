package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.zones.DistrictsResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class TimeFilterDistrictsResponse {
    @NonNull
    Iterable<DistrictsResult> results;
}
