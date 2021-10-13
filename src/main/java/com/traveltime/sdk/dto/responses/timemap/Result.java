package com.traveltime.sdk.dto.responses.timemap;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Result {
    String searchId;
    Iterable<Shape> shapes;
    Object properties; //temporary hack
}
