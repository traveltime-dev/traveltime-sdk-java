package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.traveltime.sdk.dto.common.Transportation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DepartureSearch {
    String id;
    String departureLocationId;
    Iterable<String> arrivalLocationIds;
    Transportation transportation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date departureTime;
    Integer travelTime;
    Iterable<String> properties;
    Range range;
}
