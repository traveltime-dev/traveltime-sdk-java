package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilter.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class TimeFilterResponse {
    @NonNull
    Iterable<Result> results;
}
