package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.requests.TimeFilterFastRequest;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timefilterfast.transportation.Walking;
import com.traveltime.sdk.dto.requests.timefilterfast.OneToMany;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

/**
 * Example how to get 3 closest shops
 */
public class TimeFilterExample {

    public static void main(String[] args) {
        val departureCoordinates = new Coordinates(51.41070, -0.15540);
        val departureLocationId = "Departure location";
        val departureLocation = new Location(departureLocationId, departureCoordinates);

        val arrivalLocations = Utils
            .generateLocations("shop", departureCoordinates, 0.005, 100)
            .stream()
            .map(pair -> new Location(pair.getKey(), pair.getValue()))
            .collect(Collectors.toList());
        val arrivalLocationIds = arrivalLocations.stream().map(Location::getId).collect(Collectors.toList());

        arrivalLocations.add(departureLocation);

        val request = createRequest(departureLocationId, arrivalLocationIds, arrivalLocations);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey"));
        val response = sdk.send(request);
        val res = Match(response).of(
            Case($Right($()), v ->
                "Closest shops: " + String.join(", ", Utils.findClosest(v.getResults().get(0).getLocations(), 3))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );

        System.out.println(res);
    }


    private static TimeFilterFastRequest createRequest(
        String departureLocationId,
        List<String> arrivalLocationIds,
        List<Location> locations
    ) {
        val oneToMany = new OneToMany(
            "Get the shortest path to a shop",
            departureLocationId,
            arrivalLocationIds,
            new Walking(),
            7200,
            "weekday_morning",
            Collections.singletonList(Property.TRAVEL_TIME)
        );

        val arrivalSearches = new ArrivalSearches(Collections.emptyList(), Collections.singletonList(oneToMany));

        return new TimeFilterFastRequest(locations, arrivalSearches);
    }
}
