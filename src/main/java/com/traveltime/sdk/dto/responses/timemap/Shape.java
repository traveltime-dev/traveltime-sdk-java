package com.traveltime.sdk.dto.responses.timemap;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Shape {
    List<Coordinates> shell;
    List<List<Coordinates>> holes;
}
