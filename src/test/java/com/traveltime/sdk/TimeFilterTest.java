package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterFastRequest;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.timefilter.*;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timefilterfast.ManyToOne;
import com.traveltime.sdk.dto.requests.timefilterfast.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
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

public class TimeFilterTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendTimeFilterRequest() throws IOException, RequestValidationException {
        Iterable<Location> locations = Arrays.asList(
            new Location("location1", new Coordinates(51.508930,-0.131387)),
            new Location("location2", new Coordinates(51.508824,-0.167093)),
            new Location("location3", new Coordinates(51.536067,-0.153596))
        );
        Transportation transport = new PublicTransport();

        TimeFilterRequest request = new TimeFilterRequest(
            locations,
            createDepartureSearch("location1", Arrays.asList("location2", "location3"), transport),
            createArrivalSearch(Arrays.asList("location2", "location3"), "location1", transport)
        );

        TravelTimeResponse<TimeFilterResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    @Test
    public void shouldSendTimeFilterFastRequest() throws IOException, RequestValidationException {
        Iterable<Location> locations = Arrays.asList(
            new Location("location1", new Coordinates(51.508930,-0.131387)),
            new Location("location2", new Coordinates(51.508824,-0.167093)),
            new Location("location3", new Coordinates(51.536067,-0.153596))
        );
        Transportation transport = new PublicTransport();
        ArrivalSearches arrivalSearches = new ArrivalSearches(
            createManyToOne("location1", Arrays.asList("location2", "location3"), transport),
            createOneToMany("location1", Arrays.asList("location2", "location3"), transport)
        );

        TimeFilterFastRequest request = new TimeFilterFastRequest(locations, arrivalSearches);

        TravelTimeResponse<TimeFilterFastResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private Iterable<ManyToOne> createManyToOne(
        String arrivalLocation,
        Iterable<String> departureLocations,
        Transportation transportation
    ) {
        ManyToOne manyToOne = new ManyToOne(
            "test many to one",
            arrivalLocation,
            departureLocations,
            transportation,
            900,
            "weekday_morning",
            Arrays.asList(Property.TRAVEL_TIME, Property.FARES)
        );

        return Collections.singletonList(manyToOne);
    }

    private Iterable<OneToMany> createOneToMany(
        String departureLocation,
        Iterable<String> arrivalLocations,
        Transportation transportation
    ) {
        OneToMany oneToMany = new OneToMany(
            "test one to many",
            departureLocation,
            arrivalLocations,
            transportation,
            900,
            "weekday_morning",
            Arrays.asList(Property.TRAVEL_TIME, Property.FARES)
        );

        return Collections.singletonList(oneToMany);
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
            900,
            Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE)
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
            900,
            Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES),
            new FullRange(true, 1, 300)
        );
        return Collections.singletonList(as);
    }
}
