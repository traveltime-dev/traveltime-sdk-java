package com.traveltime.sdk.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
public class TimeFilterProtoResponse {
    @NonNull
    List<Integer> travelTimes;
}
