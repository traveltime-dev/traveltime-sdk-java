package com.traveltime.sdk.dto.responses.timemap;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents a bounding box structure for time map results, containing both an overall envelope that encompasses
 * the entire area and a collection of individual rectangular boxes that make up the detailed coverage area.
 * <p>
 * This is used in time map API responses where the reachable area within a given time limit is represented as
 * a collection of geographic rectangles, with an outer envelope defining the complete bounds of the entire result set.
 */
@Value
@Builder
@Jacksonized
public class BoundingBox {

    /**
     * The overall bounding rectangle that encompasses all individual boxes in the collection.
     * This envelope represents the minimum and maximum coordinates that contain the entire
     * time map result area, providing a quick way to determine the full geographic extent
     * without iterating through all individual boxes.
     */
    @NonNull
    Rectangle envelope;

    /**
     * Collection of individual rectangular areas that make up the detailed coverage
     * of the time map result. Each rectangle represents a specific geographic region
     * that is reachable within the specified time constraints.
     * <p>
     * The union of all these boxes should fit within the envelope boundary, and together
     * they provide a more granular representation of the accessible area than the
     * envelope alone.
     */
    @NonNull
    List<Rectangle> boxes;
}
