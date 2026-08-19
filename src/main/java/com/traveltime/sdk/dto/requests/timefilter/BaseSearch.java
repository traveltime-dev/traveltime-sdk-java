package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.*;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseSearch {
    @NonNull
    String id;

    @NonNull
    Transportation transportation;

    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;

    @NonNull
    List<Property> properties;

    @Valid
    FullRange range;

    Snapping snapping;
}
