package com.traveltime.sdk.dto.responses.timefilter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Result {
    String searchId;
    Iterable<Location> locations;
    Iterable<String> unreachable;
}
