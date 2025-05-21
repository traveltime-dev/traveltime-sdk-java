package com.traveltime.sdk.dto.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a geometric shape defined by an outer boundary (shell) and optional inner holes.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Shape {
    /**
     * The outer boundary (perimeter) of the shape.
     * <p>
     * This list of coordinates defines the continuous outer shell of the shape in order.
     * The coordinates should form a closed loop, with the last coordinate connecting back
     * to the first one. For valid shapes, this list should contain at least three coordinates.
     */
    List<Coordinates> shell;

    /**
     * Inner holes or exclusion areas within the shape's outer boundary.
     * <p>
     * Each element in this list represents a hole within the shape, defined as a list of coordinates.
     * Like the shell, each hole should form a closed loop. The holes must be completely contained within the shell.
     * <p>
     * An empty list indicates a shape with no holes.
     */
    List<List<Coordinates>> holes;
}
