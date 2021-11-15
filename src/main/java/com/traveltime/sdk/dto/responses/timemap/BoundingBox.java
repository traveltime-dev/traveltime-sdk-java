package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
public class BoundingBox {
    @NonNull
    Rectangle envelope;
    @NonNull
    List<Rectangle> boxes;
}
