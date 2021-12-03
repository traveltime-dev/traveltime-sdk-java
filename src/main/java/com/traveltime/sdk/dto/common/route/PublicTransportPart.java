package com.traveltime.sdk.dto.common.route;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.traveltime.sdk.dto.common.Coordinates;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class PublicTransportPart implements Part {
    @NonNull
    Integer id;
    @NonNull
    String mode;
    @NonNull
    String directions;
    @NonNull
    @Positive(message = "distance must be greater than 0")
    Integer distance;
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;
    @NonNull
    List<Coordinates> coords;
    @NonNull
    String line;
    @NonNull
    String departureStation;
    @NonNull
    String arrivalStation;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    Date departsAt;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    Date arrivesAt;
    @NonNull
    Integer numStops;
}
