package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.Walking;
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
 * Example how to get all existing cafes within walking distance
 */
public class TimeMapExample {

    public static void main(String[] args) {
        val origin = new Coordinates(51.41070, -0.15540);
        val request = createRequest(origin);

        val locations = Utils.generateLocations("cafe", origin, 0.004, 100);

        val sdk = new TravelTimeSDK(new TravelTimeCredentials("appId", "apiKey"));
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
        return gf.createPoint(new Coordinate(coordinate.getLat(), coordinate.getLng()));
    }

    private static TimeMapWktRequest createRequest(
        Coordinates origin
    ) {
        val ds = new DepartureSearch(
            "Walking area",
            origin,
            new Walking(),
            Instant.now(),
            1800,
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
