package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeClient;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.transportation.Walking;
import com.traveltime.sdk.dto.requests.time.Time;
import com.traveltime.sdk.dto.requests.time.TimeType;
import lombok.val;

import java.time.Instant;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

public class TimeFilterExample {

    public static void main(String[] args) {
        val departureCoordinates = new Coordinates(51.41070, -0.15540);
        val departureLocation = new Location("departure location", departureCoordinates);

        val arrivalLocations = Utils
            .generateLocations("shop", departureCoordinates, 0.005, 100);

        val client = new TravelTimeClient(new TravelTimeCredentials("appId", "apiKey"));
        val response = client.getTimeFilter(
            departureLocation,
            arrivalLocations,
            new Walking(),
            3600,
            new Time(Instant.now(), TimeType.DEPARTURE)
        );

        val res = Match(response).of(
            Case($Right($()), v ->
                "Closest shops: " + String.join(", ", Utils.findClosest(v, 3))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );


        System.out.println(res);
    }
}
