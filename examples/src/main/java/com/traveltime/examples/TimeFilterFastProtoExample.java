package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.*;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

/**
 * Example showing how to find 3 closest gas stations by travel time.
 * The list of gas station coordinates is generated randomly. Here we are using driving transportation mode, but you can use
 * different ways of transportation, for example: public transport or walking.
 * This example is similar to TimeFilterExample but is using protobuf to achieve much faster response time.
 * If you're looking for real-time search we recommend to use protobuf.
 * You can find TravelTime protobuf benchmarks at - https://github.com/traveltime-dev/traveltime-benchmarks
 */
public class TimeFilterFastProtoExample {
    public static void main(String[] args) {
        val origin = new Coordinates(51.425709, -0.122061);
        val locations = Utils.generateLocations("gas station", origin, 0.004, 100);
        val destinations = locations
            .stream()
            .map(loc -> new Coordinates(loc.getValue().getLat(), loc.getValue().getLng()))
            .collect(Collectors.toList());

        val request = createRequest(origin, destinations);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey")); // Substitute your credentials here
        val response = sdk.sendProto(request);

        val res = Match(response).of(
            Case($Right($()), v ->
                "Closest gas stations: " + String.join(", ", Utils.findClosest(v.getTravelTimes(), locations, 3))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );

        System.out.println(res);
    }

    private static TimeFilterFastProtoRequest createRequest(
        Coordinates origin,
        List<Coordinates> destinations
    ) {
        val oneToMany = new OneToMany(
            origin,
            destinations,
            Transportation.DRIVING,
            7200,
            Country.NETHERLANDS
        );

        return new TimeFilterFastProtoRequest(oneToMany);
    }
}
