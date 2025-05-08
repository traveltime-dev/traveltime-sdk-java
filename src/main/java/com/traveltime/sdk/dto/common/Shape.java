package com.traveltime.sdk.dto.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Shape {
  List<Coordinates> shell;
  List<List<Coordinates>> holes;
}
