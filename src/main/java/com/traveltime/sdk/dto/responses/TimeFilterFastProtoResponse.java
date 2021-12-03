package com.traveltime.sdk.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class TimeFilterFastProtoResponse {
    @NonNull
    List<Integer> travelTimes;
}
