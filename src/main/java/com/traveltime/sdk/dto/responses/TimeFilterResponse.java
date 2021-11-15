package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilter.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class TimeFilterResponse {
    @NonNull
    List<Result> results;
}
