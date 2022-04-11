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

    /**
     * @param originCoordinate       The coordinates of location we should start the search from.
     * @param destinationCoordinates The coordinates of locations we run the search to. If the class implementing this list
     *                               does not implement the {@code RandomAccess} interface it will be internally converted into an {@code ArrayList}.
     * @param transportation         Transportation mode.
     * @param travelTime             Travel time limit.
     * @param country                The country to run the search in.
     */
    public OneToMany(@NonNull Coordinates originCoordinate, @NonNull List<Coordinates> destinationCoordinates, @NonNull Transportation transportation, @NonNull Integer travelTime, @NonNull Country country) {
        this.originCoordinate = originCoordinate;
        if (destinationCoordinates instanceof RandomAccess) {
            this.destinationCoordinates = destinationCoordinates;
        } else {
            this.destinationCoordinates = new ArrayList<>(destinationCoordinates);
        }
        this.transportation = transportation;
        this.travelTime = travelTime;
        this.country = country;
    }
}
