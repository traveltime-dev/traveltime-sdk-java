package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.routes.Result;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class RoutesResponse {
    @NonNull
    List<Result> results;
}
