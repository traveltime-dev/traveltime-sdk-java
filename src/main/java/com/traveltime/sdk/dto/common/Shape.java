package com.traveltime.sdk.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
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
