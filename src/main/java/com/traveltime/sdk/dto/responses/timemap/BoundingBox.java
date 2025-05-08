package com.traveltime.sdk.dto.responses.timemap;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class BoundingBox {
    @NonNull
    Rectangle envelope;

    @NonNull
    List<Rectangle> boxes;
}
