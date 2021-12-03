package com.traveltime.sdk.dto.requests.proto;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.*;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class OneToMany {
    @NonNull
    Coordinates originCoordinate;
    @NonNull
    List<Coordinates> destinationCoordinates;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;
    @NonNull
    Country country;
}
