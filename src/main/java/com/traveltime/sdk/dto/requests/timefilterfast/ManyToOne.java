package com.traveltime.sdk.dto.requests.timefilterfast;

import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ManyToOne {
    @NonNull
    String id;
    @NonNull
    String arrivalLocationId;
    @NonNull
    @Singular
    List<String> departureLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;
    @NonNull
    String arrivalTimePeriod;
    @NonNull
    @Singular
    List<Property> properties;
}
