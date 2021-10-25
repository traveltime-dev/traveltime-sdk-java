package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@Jacksonized
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coach implements Transportation {
    @Positive
    Integer ptChangeDelay;
    @Positive
    Integer walkingTime;
    MaxChanges maxChanges;
}
