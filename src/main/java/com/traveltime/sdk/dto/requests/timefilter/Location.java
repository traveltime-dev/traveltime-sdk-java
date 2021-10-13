package com.traveltime.sdk.dto.requests.timefilter;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Location {
    String id;
    Coordinates coords;
}
