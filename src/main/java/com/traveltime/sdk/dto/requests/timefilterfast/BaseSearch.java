package com.traveltime.sdk.dto.requests.timefilterfast;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public abstract class BaseSearch {
    @NonNull
    String id;

    @NonNull
    Transportation transportation;

    @NonNull
    Integer travelTime;

    @NonNull
    String arrivalTimePeriod;

    @NonNull
    @Singular
    List<Property> properties;

    Snapping snapping;
}
