package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.Countries;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Finds the closest shops by travel time using TravelTime's high-performance Protocol Buffers API.
 *
 * <p>This example demonstrates one-to-many travel time calculations from a single origin to multiple
 * destinations, identifying locations with the shortest driving times.
 *
 * <p><strong>Performance:</strong> Protocol Buffers provides faster response times compared to
 * the standard JSON API. Recommended for real-time applications requiring sub-second responses.
 *
 * <p><strong>Benchmarks:</strong> https://github.com/traveltime-dev/traveltime-benchmarks
 */
public class TimeFilterFastProtoExample {

    private static int MAX_TRAVEL_TIME_SECONDS = 7200; // 2 hours maximum search radius
    private static int LOCATION_COUNT = 100; // Number of test shops to generate
    private static double LOCATION_SPREAD = 0.005; // Spread around origin
    private static int CLOSEST_RESULTS_COUNT = 3; // Return top 3 closest shops

    /**
     * Demonstrates the complete shop-finding workflow:
     * <ol>
     *   <li>Generate test shop locations around London</li>
     *   <li>Calculate travel times using Protocol Buffers API</li>
     *   <li>Identify and return the 3 closest shops by driving time</li>
     * </ol>
     */
    public static void main(String[] args) {
        // TODO: Replace with your actual API credentials
        TravelTimeCredentials credentials = new TravelTimeCredentials("appId", "apiKey");
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        Either<TravelTimeError, List<Map.Entry<Coordinates, Integer>>> results = getResults(sdk);

        results.fold(TimeFilterFastProtoExample::formatErrorMessage, TimeFilterFastProtoExample::formatSuccessMessage);

        System.out.println(getResults(sdk));
    }

    public static Either<TravelTimeError, List<Map.Entry<Coordinates, Integer>>> getResults(TravelTimeSDK sdk) {
        // Starting point: London coordinates
        Coordinates origin = new Coordinates(51.41070, -0.15540);

        // Generate simulated shop locations for testing
        List<Coordinates> shopLocations = generateShopCoordinates(origin);

        // Create an optimized protobuf request
        TimeFilterFastProtoRequest request = buildTimeFilterRequest(origin, shopLocations);

        // Execute search and handle response
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);

        return response.map(r -> IntStream.range(0, Math.min(r.getTravelTimes().size(), shopLocations.size()))
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(
                        shopLocations.get(i), r.getTravelTimes().get(i)))
                .collect(Collectors.toList()));
    }

    /**
     * Generates random shop coordinates within a small radius of the origin point.
     *
     * @return list of shop coordinates for testing
     */
    private static List<Coordinates> generateShopCoordinates(Coordinates origin) {
        return Utils.generateLocations("shop", origin, LOCATION_SPREAD, LOCATION_COUNT).stream()
                .map(Map.Entry::getValue) // Extract coordinates from name-coordinate pairs
                .collect(Collectors.toList());
    }

    /**
     * Builds a Protocol Buffers request optimized for one-to-many travel time calculations.
     *
     * @return configured protobuf request ready for API submission
     */
    private static TimeFilterFastProtoRequest buildTimeFilterRequest(
            Coordinates origin, List<Coordinates> destinations) {

        return TimeFilterFastProtoRequest.builder()
                .requestType(RequestType.ONE_TO_MANY)
                .country(Countries.UNITED_KINGDOM)
                .originCoordinate(origin)
                .destinationCoordinates(destinations)
                .transportation(Transportation.Modes.DRIVING_FERRY)
                .travelTime(MAX_TRAVEL_TIME_SECONDS)
                .withDistance(false) // Optimize for speed - skip distance calculations
                .build();
    }

    /**
     * Formats API errors into user-friendly messages.
     */
    private static String formatErrorMessage(TravelTimeError error) {
        return String.format("Search failed: %s", error.getMessage());
    }

    /**
     * Processes a successful API response and formats the closest shops for display.
     *
     * @return formatted string listing the closest shops
     */
    private static String formatSuccessMessage(List<Map.Entry<Coordinates, Integer>> shopsWithTravelTimes) {
        if (shopsWithTravelTimes.isEmpty()) {
            return "No shops found within " + (MAX_TRAVEL_TIME_SECONDS / 60)
                    + " minutes - try increasing travel time limit";
        }

        // Find the shops with the shortest travel times
        List<Coordinates> closestShop = Utils.findClosest(
                shopsWithTravelTimes.stream().map(Map.Entry::getValue).collect(Collectors.toList()),
                shopsWithTravelTimes.stream().map(Map.Entry::getKey).collect(Collectors.toList()),
                CLOSEST_RESULTS_COUNT);

        return String.format(
                "Top %d closest shops by driving time: %s",
                CLOSEST_RESULTS_COUNT, String.join(", ", closestShop.toString()));
    }
}
