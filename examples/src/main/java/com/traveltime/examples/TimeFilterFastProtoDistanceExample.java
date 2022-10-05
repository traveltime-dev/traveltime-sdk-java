package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoDistanceRequest;
import com.traveltime.sdk.dto.requests.protodistance.Country;
import com.traveltime.sdk.dto.requests.protodistance.Transportation;
import lombok.val;

import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

/**
 * Example showing how to find 3 closest gas stations by travel time and distance.
 * The list of gas station coordinates is generated randomly. Here we are using driving+ferry transportation mode,
 * but you can also use walking+ferry.
 * This example is similar to TimeFilterFastProtoExample, but besides travelTime it returns distances.
 */
public class TimeFilterFastProtoDistanceExample {
    public static void main(String[] args) {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        val locations = Utils.generateLocations("gas station", origin, 0.004, 100);
        val destinations = locations
            .stream()
            .map(loc -> new Coordinates(loc.getValue().getLat(), loc.getValue().getLng()))
            .collect(Collectors.toList());
        val request = new TimeFilterFastProtoDistanceRequest(
            origin,
            destinations,
            3200,
            Transportation.DRIVING_FERRY,
            Country.UNITED_KINGDOM
        );

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey")); // Substitute your credentials here
        val response = sdk.sendProto(request);

        val res = Match(response).of(
            Case($Right($()), v ->
                "Closest gas stations by travelTime: "
                    + String.join(", ", Utils.findClosest(v.getTravelTimes(), locations, 3))
                    + ". Closest gas stations by distance: "
                    + String.join(", ", Utils.findClosest(v.getDistances(), locations, 3))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );

        System.out.println(res);
    }
}