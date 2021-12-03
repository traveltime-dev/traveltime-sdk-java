package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.BoxesResult;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapBoxesResponse {
    @NonNull
    List<BoxesResult> results;
}
