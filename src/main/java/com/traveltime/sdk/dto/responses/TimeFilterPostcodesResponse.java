package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.postcodes.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterPostcodesResponse {
    @NonNull
    List<Result> results;
}
