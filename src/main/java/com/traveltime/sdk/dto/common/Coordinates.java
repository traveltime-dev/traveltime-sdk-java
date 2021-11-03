package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder(builderMethodName = "internalBuilder")
@Jacksonized
@AllArgsConstructor
public class Coordinates {
    double lat;
    double lng;

    public static CoordinatesBuilder builder(double lat, double lng) {
        return internalBuilder().lat(lat).lng(lng);
    }
}
