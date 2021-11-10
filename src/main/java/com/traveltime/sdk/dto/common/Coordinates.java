package com.traveltime.sdk.dto.common;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public class Coordinates {
    double lat;
    double lng;
}
