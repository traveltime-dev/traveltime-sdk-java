package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class BoxesResult {
    @NonNull
    String searchId;
    @NonNull
    Iterable<BoundingBox> boundingBoxes;
    @NonNull
    ResponseProperties properties;
}
