package com.traveltime.sdk.dto.responses.timemap;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Shape {
    Iterable<Coordinates> shell;
    Iterable<Iterable<Coordinates>> holes;
}
