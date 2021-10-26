package com.traveltime.sdk.dto.requests.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Setter(AccessLevel.NONE)
public class Range {
    Boolean enabled;
    int maxResults;
    int width;
}