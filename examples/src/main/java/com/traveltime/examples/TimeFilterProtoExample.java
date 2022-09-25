package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.*;
import lombok.val;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class TimeFilterProtoExample {
    public static void main(String[] args) {
        val origin = new Coordinates(53.1234, 54.2314);
        val locations = Utils.generateLocations("cafe", origin, 0.4, 2);
        val destinations = locations
            .stream()
            .map(loc -> new Coordinates(loc.getValue().getLat(), loc.getValue().getLng()))
            .collect(Collectors.toList());

        val request = oneToMany(origin, destinations);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey"));
        val response = sdk.sendProto(request);
        System.out.println(response);
    }

    private static TimeFilterFastProtoRequest oneToMany(
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
