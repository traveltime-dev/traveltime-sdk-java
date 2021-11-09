package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.RoutesRequest;
import com.traveltime.sdk.dto.requests.routes.*;
import com.traveltime.sdk.dto.responses.RoutesResponse;
import com.traveltime.sdk.dto.responses.TravelTimeResponse;
import com.traveltime.sdk.exceptions.RequestValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class RoutesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendRoutesRequest() throws IOException, RequestValidationException {
        Iterable<Location> locations = Arrays.asList(
            new Location("location1", new Coordinates(51.508930,-0.131387)),
            new Location("location2", new Coordinates(51.508824,-0.167093)),
            new Location("location3", new Coordinates(51.536067,-0.153596))
        );
        Transportation transport = new PublicTransport();

        RoutesRequest request = new RoutesRequest(
            locations,
            createDepartureSearch("location1", Arrays.asList("location2", "location3"), transport),
            createArrivalSearch(Arrays.asList("location2", "location3"), "location1", transport)
        );

        TravelTimeResponse<RoutesResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private Iterable<DepartureSearch> createDepartureSearch(
        String departureLocation,
        Iterable<String> arrivalLocations,
        Transportation transportation
    ) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            departureLocation,
            arrivalLocations,
            transportation,
            Date.from(Instant.now()),
            Arrays.asList(Properties.TRAVEL_TIME, Properties.DISTANCE, Properties.ROUTE)
        );
        return Collections.singletonList(ds);
    }

    private Iterable<ArrivalSearch> createArrivalSearch(
        Iterable<String> departureLocations,
        String arrivalLocation,
        Transportation transportation
    ) {
        ArrivalSearch as = new ArrivalSearch(
        "Test arrival search",
            departureLocations,
            arrivalLocation,
            transportation,
            Date.from(Instant.now()),
            Arrays.asList(Properties.TRAVEL_TIME, Properties.DISTANCE, Properties.ROUTE, Properties.FARES),
            new FullRange(true, 1, 300)
        );
        return Collections.singletonList(as);
    }

}
