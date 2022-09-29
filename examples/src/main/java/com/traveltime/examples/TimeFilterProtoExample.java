package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeClient;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import lombok.val;

import static io.vavr.API.*;
import static io.vavr.Patterns.*;

public class TimeFilterProtoExample {
    public static void main(String[] args) {
        val departureCoordinates = new Coordinates(51.425709, -0.122061);

        val arrivalLocations = Utils
            .generateLocations("gas station", departureCoordinates, 0.005, 100);

        val client = new TravelTimeClient(new TravelTimeCredentials("appId", "apiKey"));
        val response = client.getTimeFilterProto(
            departureCoordinates,
            arrivalLocations,
            Transportation.DRIVING,
            3600,
            Country.NETHERLANDS
        );

        val res = Match(response).of(
            Case($Right($()), v ->
                "Closest stations: " + String.join(", ", Utils.findClosestProto(v.getProtoResults(), 3))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );


        System.out.println(res);
    }
}
