package com.traveltime.sdk.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class GeohashFastProtoResponse {
    @NonNull
    List<String> ids;

    @NonNull
    List<Integer> minTravelTimes;

    @NonNull
    List<Integer> maxTravelTimes;

    @NonNull
    List<Integer> meanTravelTimes;
}
