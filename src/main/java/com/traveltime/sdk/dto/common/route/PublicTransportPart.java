package com.traveltime.sdk.dto.common.route;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;
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
    Integer distance;
    @NonNull
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
    LocalTime departsAt;
    @NonNull
    LocalTime arrivesAt;
    @NonNull
    Integer numStops;
}
