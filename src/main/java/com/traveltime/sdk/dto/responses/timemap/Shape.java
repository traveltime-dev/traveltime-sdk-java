package com.traveltime.sdk.dto.responses.timemap;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Shape {
    Iterable<Coordinates> shell;
    Iterable<Coordinates> holes;
}
