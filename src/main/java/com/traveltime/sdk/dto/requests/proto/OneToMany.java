package com.traveltime.sdk.dto.requests.proto;

import com.traveltime.sdk.dto.common.Coordinates;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

@Value
@Builder
@With
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

    public OneToMany(@NonNull Coordinates originCoordinate, @NonNull List<Coordinates> destinationCoordinates, @NonNull Transportation transportation, @NonNull Integer travelTime, @NonNull Country country) {
        this.originCoordinate = originCoordinate;
        if(destinationCoordinates instanceof RandomAccess) {
            this.destinationCoordinates = destinationCoordinates;
        } else {
            this.destinationCoordinates = new ArrayList<>(destinationCoordinates);
        }
        this.transportation = transportation;
        this.travelTime = travelTime;
        this.country = country;
    }
}
