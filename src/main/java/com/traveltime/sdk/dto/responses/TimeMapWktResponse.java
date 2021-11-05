package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.WktResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class TimeMapWktResponse {
    @NonNull
    Iterable<WktResult> results;
}
