package com.traveltime.sdk.dto.responses;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@AllArgsConstructor
public class ProtoResponse {
    @NonNull
    List<Integer> travelTimes;

    public static ProtoResponse merge(List<ProtoResponse> responses) {
        ArrayList<Integer> times = new ArrayList<>();
        for(ProtoResponse response : responses) {
            times.addAll(response.travelTimes);
        }
        return new ProtoResponse(times);
    }
}
