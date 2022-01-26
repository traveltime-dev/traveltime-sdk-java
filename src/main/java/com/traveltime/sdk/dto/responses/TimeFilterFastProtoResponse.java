package com.traveltime.sdk.dto.responses;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
public class TimeFilterFastProtoResponse {
    @NonNull
    List<Integer> travelTimes;

    static public TimeFilterFastProtoResponse merge(List<TimeFilterFastProtoResponse> responses) {
        ArrayList<Integer> times = new ArrayList<>();
        for(TimeFilterFastProtoResponse response : responses) {
            times.addAll(response.travelTimes);
        }
        return new TimeFilterFastProtoResponse(times);
    }
}
