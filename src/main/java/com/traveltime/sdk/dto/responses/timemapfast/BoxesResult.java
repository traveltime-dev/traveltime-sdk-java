package com.traveltime.sdk.dto.responses.timemapfast;

import com.traveltime.sdk.dto.responses.timemap.BoundingBox;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class BoxesResult {
    @NonNull
    String searchId;
    @NonNull
    List<BoundingBox> boundingBoxes;
}
