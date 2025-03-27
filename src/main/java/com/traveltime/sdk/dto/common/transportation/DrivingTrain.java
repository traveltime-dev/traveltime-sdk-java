package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
import com.traveltime.sdk.dto.common.MaxChanges;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrivingTrain implements Transportation {
    @Positive(message = "ptChangeDelay must be greater than 0")
    Integer ptChangeDelay;
    @Positive(message = "drivingTimeToStation must be greater than 0")
    Integer drivingTimeToStation;
    @Positive(message = "parkingTime must be greater than 0")
    Integer parkingTime;
    @Positive(message = "walkingTime must be greater than 0")
    Integer walkingTime;
    @Valid
    MaxChanges maxChanges;
    DrivingTrafficModel trafficModel;
}
