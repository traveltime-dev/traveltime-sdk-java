package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemapfast.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapFastResponse {
    @NonNull
    List<Result> results;
}
