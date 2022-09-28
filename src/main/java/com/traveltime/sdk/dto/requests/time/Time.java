package com.traveltime.sdk.dto.requests.time;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@AllArgsConstructor
public class Time {
    @NonNull
    Instant value;

    @NonNull
    TimeType timeType;
}
