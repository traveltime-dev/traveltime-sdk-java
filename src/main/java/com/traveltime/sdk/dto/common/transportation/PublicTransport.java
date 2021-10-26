package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;


@Getter
@Jacksonized
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublicTransport implements Transportation {
    @Positive(message = "ptChangeDelay must be greater than 0")
    Integer ptChangeDelay;
    @Positive(message = "walkingTime must be greater than 0")
    Integer walkingTime;
    @Valid
    MaxChanges maxChanges;
}
