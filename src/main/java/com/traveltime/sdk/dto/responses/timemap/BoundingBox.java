package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class BoundingBox {
    @NonNull
    Container envelope;
    @NonNull
    Iterable<Container> boxes;
}
