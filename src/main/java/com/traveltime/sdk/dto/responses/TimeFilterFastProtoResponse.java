package com.traveltime.sdk.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class TimeFilterFastProtoResponse {
    @NonNull
    List<Integer> travelTimes;

    List<Integer> distances;
}
