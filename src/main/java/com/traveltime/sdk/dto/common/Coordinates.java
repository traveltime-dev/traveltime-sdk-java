package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Coordinates {
    @NonNull
    Double lat;
    @NonNull
    Double lng;
}
