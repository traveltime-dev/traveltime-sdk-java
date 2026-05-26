package com.traveltime.sdk.dto.requests.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.*;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
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
    @Singular
    List<Property> properties;

    @Valid
    FullRange range;

    Snapping snapping;
}
