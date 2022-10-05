package com.traveltime.sdk.dto.responses;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class TimeFilterFastProtoDistanceResponse {
    @NonNull
    List<Integer> travelTimes;
    @NonNull
    List<Integer> distances;
}
