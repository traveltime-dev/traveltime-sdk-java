package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.DrivingTrafficModel;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.RoutesRequest;
import com.traveltime.sdk.dto.requests.routes.*;
import com.traveltime.sdk.dto.responses.RoutesResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class RoutesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendRoutesRequests() {
        List<Location> locations = Arrays.asList(
                new Location("location1", new Coordinates(51.508930, -0.131387)),
                new Location("location2", new Coordinates(51.508824, -0.167093)),
                new Location("location3", new Coordinates(51.536067, -0.153596)));

        List<Transportation> transportationModes = Arrays.<Transportation>asList(
                PublicTransport.builder().build(),
                Driving.builder().trafficModel(DrivingTrafficModel.OPTIMISTIC).build());

        transportationModes.forEach(transportation -> {
            RoutesRequest request = new RoutesRequest(
                    locations,
                    createDepartureSearch("location1", Arrays.asList("location2", "location3"), transportation),
                    createArrivalSearch(Arrays.asList("location2", "location3"), "location1", transportation));
            Either<TravelTimeError, RoutesResponse> response = sdk.send(request);
            Common.assertResponseIsRight(response);
        });
    }

    private List<DepartureSearch> createDepartureSearch(
            String departureLocation, List<String> arrivalLocations, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
                "Test departure search",
                departureLocation,
                arrivalLocations,
                transportation,
                Instant.now(),
                Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE),
                new FullRange(true, 1, 300),
                null);
        return Collections.singletonList(ds);
    }

    private List<ArrivalSearch> createArrivalSearch(
            List<String> departureLocations, String arrivalLocation, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
                "Test arrival search",
                departureLocations,
                arrivalLocation,
                transportation,
                Instant.now(),
                Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES),
                new FullRange(true, 1, 300),
                null);
        return Collections.singletonList(as);
    }
}
