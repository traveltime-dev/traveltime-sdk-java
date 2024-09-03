package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.TimeMapWktRequest;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.responses.timemap.WktResult;
import javafx.util.Pair;
import lombok.val;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Left;
import static io.vavr.Patterns.$Right;

/**
 * Example showing how to get all existing cafes by travelTime within driving distance.
 * The list of cafe coordinates is generated randomly.
 * Here we are using driving transportation mode, but you can use different ways of
 * transportation, for example: public transport or walking.
 */
public class TimeMapExample {

    public static void main(String[] args) {
        val origin = new Coordinates(51.41070, -0.15540);
        val request = createRequest(origin);

        val locations = Utils.generateLocations("cafe", origin, 0.5, 1000);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey")); // Substitute your credentials here
        val response = sdk.send(request);

        val res = Match(response).of(
            Case($Right($()), v ->
                "All cafes in area: " + String.join(", ", locationsInArea(locations, v.getResults().get(0)))
            ),
            Case($Left($()), v -> "Failed with error: " + v.getMessage())
        );

        System.out.println(res);
    }

    private static List<String> locationsInArea(List<Pair<String, Coordinates>> locations, WktResult result) {
        return locations
            .stream()
            .filter(location -> result.getShape().contains(getPoint(location) ))
            .map(Pair::getKey)
            .collect(Collectors.toList());
    }

    private static Point getPoint(Pair<String, Coordinates> pair) {
        val gf = new GeometryFactory();
        val coordinate = pair.getValue();
        return gf.createPoint(new Coordinate(coordinate.getLng(), coordinate.getLat()));
    }

    private static TimeMapWktRequest createRequest(
        Coordinates origin
    ) {
        val ds = new DepartureSearch(
            "Walking area",
            origin,
            Driving.builder().build(),
            Instant.now(),
            600,
            null,
            null,
            false,
            false,
            null
        );

        return new TimeMapWktRequest(
            Collections.singletonList(ds),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            false
        );
    }
}
