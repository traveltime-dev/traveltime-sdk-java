package com.traveltime.sdk.dto.requests.timefilter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Range {
    Boolean enabled;
    Integer maxResults;
    Integer width;
}
