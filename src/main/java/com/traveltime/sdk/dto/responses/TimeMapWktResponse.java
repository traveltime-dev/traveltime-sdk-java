package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.WktResult;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapWktResponse {
    @NonNull
    List<WktResult> results;
}
