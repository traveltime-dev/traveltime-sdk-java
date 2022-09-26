package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.*;
import javafx.util.Pair;
import lombok.val;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;


public class TimeFilterProtoExample {
    public static void main(String[] args) {
        val origin = new Coordinates(51.425709, -0.122061);
        val locations = Utils.generateLocations("gas station", origin, 0.004, 100);
        val destinations = locations
            .stream()
            .map(loc -> new Coordinates(loc.getValue().getLat(), loc.getValue().getLng()))
            .collect(Collectors.toList());

        val request = createRequest(origin, destinations);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey"));
        val response = sdk.sendProto(request);

        val res = Match(response).of(
            Case($Right($()), v -> "Closest gas stations: " + String.join(", ", findClosest(v.getTravelTimes(), locations, 3))),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );

        System.out.println(res);
    }

    private static List<String> findClosest(
        List<Integer> travelTimes,
        List<Pair<String, Coordinates>> locations,
        int top
    ) {
        return IntStream
            .range(0, Math.min(travelTimes.size(), locations.size()))
            .mapToObj(i -> new Pair<>(locations.get(i).getKey(), travelTimes.get(i)))
            .sorted(Comparator.comparing(Pair::getValue))
            .map(Pair::getKey)
            .collect(Collectors.toList())
            .subList(0, top);
    }

    private static TimeFilterFastProtoRequest createRequest(
        Coordinates origin,
        List<Coordinates> destinations
    ) {
        OneToMany oneToMany = new OneToMany(
            origin,
            destinations,
            Transportation.WALKING,
            7200,
            Country.NETHERLANDS
        );

        return new TimeFilterFastProtoRequest(oneToMany);
    }
}
