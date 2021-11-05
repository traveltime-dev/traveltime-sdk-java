package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Rectangle {
    double minLat;
    double maxLat;
    double minLng;
    double maxLng;
}
