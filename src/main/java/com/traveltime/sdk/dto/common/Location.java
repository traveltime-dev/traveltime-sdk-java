package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
@AllArgsConstructor
public class Location {
    @NonNull
    String id;
    @NonNull
    Coordinates coords;
}
