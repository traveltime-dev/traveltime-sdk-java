package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.routes.Result;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class RoutesResponse {
    Iterable<Result> results;
}
