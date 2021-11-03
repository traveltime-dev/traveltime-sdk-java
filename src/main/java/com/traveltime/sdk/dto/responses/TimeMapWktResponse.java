package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.SingleSearchResult;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class TimeMapWktResponse {
    Iterable<SingleSearchResult> results;
}
