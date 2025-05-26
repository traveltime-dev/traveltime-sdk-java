package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportationfast.DrivingAndFerry;
import com.traveltime.sdk.dto.requests.TimeFilterFastRequest;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timefilterfast.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Demonstrates how to find the 3 closest shops by travel time using the TravelTime API.
 * <p>
 * This example performs a one-to-many time filter search from a single departure point
 * to multiple randomly generated shop locations, then identifies the destinations with
 * the shortest travel times during weekday morning hours.
 * <p>
 * Key features demonstrated:
 * - One-to-many travel time calculations using driving mode
 * - Time period filtering (weekday morning traffic patterns)
 * - Location generation and management
 * - Functional error handling using Either pattern
 * - Result processing to find the closest destinations
 */
public class TimeFilterFastExample {

    // Configuration constants
    private static int MAX_TRAVEL_TIME_SECONDS = 7200; // 2 hours
    private static int LOCATION_COUNT = 100;
    private static double LOCATION_SPREAD = 0.005;
    private static int CLOSEST_RESULTS_COUNT = 3;

    /**
     * Main execution method that demonstrates the complete workflow:
     * 1. Generates departure and arrival locations
     * 2. Creates and sends time filter request
     * 3. Processes the response to find the closest shops
     * 4. Handles success/error cases using functional programming patterns
     */
    public static void main(String[] args) {
        // TODO: Replace with your actual API credentials
        TravelTimeCredentials credentials = new TravelTimeCredentials("appId", "apiKey");
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        String result = executeTimeFilterSearch(sdk)
                .fold(TimeFilterFastExample::formatErrorMessage, TimeFilterFastExample::formatSuccessMessage);

        System.out.println(result);
    }

    /**
     * Executes the complete time filter search workflow.
     *
     * @return Either containing error details or successful response with travel times
     */
    public static Either<TravelTimeError, TimeFilterFastResponse> executeTimeFilterSearch(TravelTimeSDK sdk) {
        // Setup departure location (coordinates in London, UK)
        Coordinates departureCoordinates = new Coordinates(51.41070, -0.15540);
        String departureLocationId = "departure_london_center";
        Location departureLocation = new Location(departureLocationId, departureCoordinates);

        // Generate random shop locations around the departure point
        List<Location> arrivalLocations = generateShopLocations(departureCoordinates);
        List<String> arrivalLocationIds = extractLocationIds(arrivalLocations);

        // Combine all locations for the request (departure + arrivals)
        List<Location> allLocations = combineLocations(departureLocation, arrivalLocations);

        // Build and send the API request
        TimeFilterFastRequest request = buildTimeFilterRequest(departureLocationId, arrivalLocationIds, allLocations);

        return sdk.send(request);
    }

    /**
     * Generates a list of shop locations around the specified departure coordinates.
     *
     * @return list of Location objects representing shop destinations
     */
    private static List<Location> generateShopLocations(Coordinates departureCoordinates) {
        return Utils.generateLocations("shop", departureCoordinates, LOCATION_SPREAD, LOCATION_COUNT).stream()
                .map(pair -> new Location(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Extracts location IDs from a list of Location objects.
     *
     * @return list of location ID strings
     */
    private static List<String> extractLocationIds(List<Location> locations) {
        return locations.stream().map(Location::getId).collect(Collectors.toList());
    }

    /**
     * Combines departure location with arrival locations into a single list.
     *
     * @return a combined list containing all locations
     */
    private static List<Location> combineLocations(Location departureLocation, List<Location> arrivalLocations) {
        ArrayList<Location> allLocations = new ArrayList<>(arrivalLocations);
        allLocations.add(departureLocation);
        return allLocations;
    }

    /**
     * Builds a TimeFilterFastRequest configured for one-to-many shop search.
     *
     * @return configured request ready for API submission
     */
    private static TimeFilterFastRequest buildTimeFilterRequest(
            String departureLocationId, List<String> arrivalLocationIds, List<Location> allLocations) {

        OneToMany oneToMany = OneToMany.builder()
                .id("find_closest_shops") // Descriptive ID for this search
                .departureLocationId(departureLocationId)
                .arrivalLocationIds(arrivalLocationIds)
                .transportation(new DrivingAndFerry())
                .travelTime(MAX_TRAVEL_TIME_SECONDS)
                .arrivalTimePeriod("weekday_morning") // Considers morning traffic patterns
                .properties(Collections.singletonList(Property.TRAVEL_TIME)) // Only return travel time data
                .build();

        ArrivalSearches arrivalSearches = new ArrivalSearches(
                Collections.emptyList(), // No many-to-one searches
                Collections.singletonList(oneToMany) // Single one-to-many search
                );

        return new TimeFilterFastRequest(allLocations, arrivalSearches);
    }

    /**
     * Formats error messages for display.
     *
     * @param error the TravelTimeError containing error details
     * @return formatted error message string
     */
    private static String formatErrorMessage(TravelTimeError error) {
        return String.format("Search failed: %s", error.getMessage());
    }

    /**
     * Formats successful response for display.
     *
     * @return formatted success message with closest shop names
     */
    private static String formatSuccessMessage(TimeFilterFastResponse response) {
        if (response.getResults().isEmpty()) {
            return "No results found - try increasing search radius or travel time limit";
        }

        List<String> closestShops =
                Utils.findClosest(response.getResults().get(0).getLocations(), CLOSEST_RESULTS_COUNT);

        return String.format("%d closest shops: %s", CLOSEST_RESULTS_COUNT, String.join(", ", closestShops));
    }
}
