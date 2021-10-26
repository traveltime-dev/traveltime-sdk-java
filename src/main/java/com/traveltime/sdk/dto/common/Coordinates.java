package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder(builderMethodName = "internalBuilder")
@Jacksonized
@AllArgsConstructor
public class Coordinates {
    @NonNull
    Double lat;
    @NonNull
    Double lng;

    public static CoordinatesBuilder builder(Double lat, Double lng) {
        return internalBuilder().lat(lat).lng(lng);
    }
}
