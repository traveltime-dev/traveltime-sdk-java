package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.TimeMapWktRequest;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.timemap.WktResult;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

/**
 * Example showing how to find cafés within travel time using TravelTime API.
 * Creates an isochrone map and filters café locations based on reachability.
 */
public class TimeMapExample {

    private static final int TRAVEL_TIME_SECONDS = 600; // 10 minutes
    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    public static void main(String[] args) {
        // TODO: Replace with your actual API credentials
        TravelTimeCredentials credentials = new TravelTimeCredentials("appId", "apiKey");
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        // Set origin point (London coordinates in this example)
        Coordinates origin = new Coordinates(51.41070, -0.15540);

        // Generate sample cafe locations
        List<Map.Entry<String, Coordinates>> cafes = Utils.generateLocations("cafe", origin, 0.5, 100);

        // Find cafés within travel time
        List<String> reachableCafes = findCafesWithinTravelTime(sdk, origin, cafes, TRAVEL_TIME_SECONDS);

        // Format and display results
        String result = reachableCafes.isEmpty()
                ? "No cafes found within travel time"
                : "Cafes within travel time: " + String.join(", ", reachableCafes);

        System.out.println(result);
    }

    /**
     * Finds cafés within travel time from the origin point.
     * Returns an empty list if the API call fails or no cafés are found.
     *
     * @return List of café names within travel time
     */
    public static List<String> findCafesWithinTravelTime(
            TravelTimeSDK sdk,
            Coordinates origin,
            List<Map.Entry<String, Coordinates>> cafes,
            Integer travelTimeSeconds) {
        // Create a time map request for 10-minute driving isochrone
        TimeMapWktRequest request = createRequest(origin, travelTimeSeconds);
        Either<TravelTimeError, TimeMapWktResponse> response = sdk.send(request);

        return response.fold(
                error -> Collections.emptyList(), // Left case: return an empty list on error
                timeMapResponse -> extractReachableCafes(cafes, timeMapResponse) // Right case: process success
                );
    }

    private static List<String> extractReachableCafes(
            List<Map.Entry<String, Coordinates>> cafes, TimeMapWktResponse response) {
        // Check if API returned any reachable area
        if (response.getResults().isEmpty()) {
            return Collections.emptyList();
        }

        // Filter cafés that fall within the driving isochrone
        return findCafesInArea(cafes, response.getResults().get(0));
    }

    private static List<String> findCafesInArea(List<Map.Entry<String, Coordinates>> cafes, WktResult areaResult) {
        // Stream through all café locations and filter those within the isochrone shape
        return cafes.stream()
                .filter(cafe -> isLocationInArea(cafe.getValue(), areaResult))
                .map(Map.Entry::getKey) // Extract cafe names
                .collect(Collectors.toList());
    }

    private static boolean isLocationInArea(Coordinates coordinates, WktResult areaResult) {
        // Convert TravelTime coordinates to JTS Point for geometric operations
        Point point = GEOMETRY_FACTORY.createPoint(new Coordinate(coordinates.getLng(), coordinates.getLat()));
        // Check if the point is contained within the isochrone polygon
        return areaResult.getShape().contains(point);
    }

    private static TimeMapWktRequest createRequest(Coordinates origin, Integer travelTime) {
        // Configure departure search with driving transportation
        DepartureSearch search = new DepartureSearch(
                "driving_area", // Search ID for identification
                origin, // Starting point coordinates
                Driving.builder().build(), // Transportation mode (driving)
                Instant.now(), // Departure time (now)
                travelTime, // Maximum travel time
                null,
                null,
                false,
                false,
                null // Optional parameters (disabled)
                );

        // Build time map request with the departure search
        return new TimeMapWktRequest(
                Collections.singletonList(search), // Departure searches
                Collections.emptyList(), // Arrival searches (none)
                Collections.emptyList(), // Unions (none)
                Collections.emptyList(), // Intersections (none)
                false // Snapping disabled
                );
    }
}
