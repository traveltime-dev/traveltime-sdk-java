package com.traveltime.sdk.dto.requests.timefilterfast;

import com.traveltime.sdk.dto.common.Property;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class OneToMany {
    @NonNull
    String id;
    @NonNull
    String departureLocationId;
    @NonNull
    List<String> arrivalLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;
    @NonNull
    String arrivalTimePeriod;
    @NonNull
    List<Property> properties;
}
