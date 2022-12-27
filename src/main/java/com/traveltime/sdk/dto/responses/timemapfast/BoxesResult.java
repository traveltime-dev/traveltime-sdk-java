package com.traveltime.sdk.dto.responses.timemapfast;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.traveltime.sdk.dto.responses.timemap.BoundingBox;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoxesResult {
    @NonNull
    String searchId;
    @NonNull
    List<BoundingBox> boundingBoxes;
}
