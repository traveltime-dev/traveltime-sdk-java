package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilterfast.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFastResponse {
    @NonNull
    List<Result> results;
}
