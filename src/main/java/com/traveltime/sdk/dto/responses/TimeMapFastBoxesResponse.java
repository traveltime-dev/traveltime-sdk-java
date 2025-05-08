package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemapfast.BoxesResult;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapFastBoxesResponse {
    @NonNull
    List<BoxesResult> results;
}
