package com.traveltime.sdk.dto.requests.proto;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Builder
@Getter
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
}
