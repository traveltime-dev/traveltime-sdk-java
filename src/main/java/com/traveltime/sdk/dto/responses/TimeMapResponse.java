package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class TimeMapResponse {
    @NonNull
    List<Result> results;
}
