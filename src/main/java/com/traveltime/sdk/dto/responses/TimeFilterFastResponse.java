package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilterfast.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class TimeFilterFastResponse {
    @NonNull
    Iterable<Result> results;
}
