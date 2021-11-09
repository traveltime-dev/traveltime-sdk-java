package com.traveltime.sdk.dto.requests.timefilterfast;

import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class OneToMany {
    @NonNull
    String id;
    @NonNull
    String departureLocationId;
    @NonNull
    Iterable<String> arrivalLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;
    @NonNull
    String arrivalTimePeriod;
    @NonNull
    Iterable<Property> properties;
}
