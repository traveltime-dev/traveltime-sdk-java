package com.traveltime.sdk.dto.requests.timefilterfast;

import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class ManyToOne {
    @NonNull
    String id;
    @NonNull
    String arrivalLocationId;
    @NonNull
    List<String> departureLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;
    @NonNull
    String arrivalTimePeriod;
    @NonNull
    List<Property> properties;
}