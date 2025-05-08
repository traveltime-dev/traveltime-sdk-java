package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapResponse {
    @NonNull
    List<Result> results;
}
