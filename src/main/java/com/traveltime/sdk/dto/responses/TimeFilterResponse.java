package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilter.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterResponse {
    @NonNull
    List<Result> results;
}
