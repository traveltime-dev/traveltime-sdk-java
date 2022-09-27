package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Coordinates {
    @NonNull
    Double lat;
    @NonNull
    Double lng;
}
