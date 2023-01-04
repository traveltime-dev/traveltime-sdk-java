package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemapfast.WktResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapFastWktResponse {
    @NonNull
    List<WktResult> results;
}
