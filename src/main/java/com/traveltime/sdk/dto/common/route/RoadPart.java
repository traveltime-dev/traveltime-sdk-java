package com.traveltime.sdk.dto.common.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoadPart implements Part {
    int id;
    @NonNull
    String mode;
    @NonNull
    String directions;
    @Positive(message = "distance must be greater than 0")
    int distance;
    @Positive(message = "travelTime must be greater than 0")
    int travelTime;
    @NonNull
    List<Coordinates> coords;
    String road;
    String turn;
}
