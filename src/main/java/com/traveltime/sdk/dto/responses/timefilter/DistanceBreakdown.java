package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class DistanceBreakdown {
    String mode;
    Integer distance;
}
