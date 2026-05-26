package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportationfast.DrivingAndPublicTransport;
import com.traveltime.sdk.dto.requests.TimeFilterFastRequest;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.timefilter.*;
import com.traveltime.sdk.dto.requests.timefilterfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timefilterfast.ManyToOne;
import com.traveltime.sdk.dto.requests.timefilterfast.OneToMany;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TimeFilterTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeFilterRequest() {
        List<Location> locations = Arrays.asList(
                new Location("location1", new Coordinates(51.508930, -0.131387)),
                new Location("location2", new Coordinates(51.508824, -0.167093)),
                new Location("location3", new Coordinates(51.536067, -0.153596)));
        TimeFilterRequest request = new TimeFilterRequest(
                locations,
                createDepartureSearch("location1", Arrays.asList("location2", "location3")),
                createArrivalSearch(Arrays.asList("location2", "location3"), "location1"));

        Either<TravelTimeError, TimeFilterResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeFilterFastRequest() {
        List<Location> locations = Arrays.asList(
                new Location("location1", new Coordinates(51.508930, -0.131387)),
                new Location("location2", new Coordinates(51.508824, -0.167093)),
                new Location("location3", new Coordinates(51.536067, -0.153596)));
        ArrivalSearches arrivalSearches = new ArrivalSearches(
                createManyToOne("location1", Arrays.asList("location2", "location3")),
                createOneToMany("location1", Arrays.asList("location2", "location3")));

        TimeFilterFastRequest request = new TimeFilterFastRequest(locations, arrivalSearches);

        Either<TravelTimeError, TimeFilterFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private List<ManyToOne> createManyToOne(String arrivalLocation, List<String> departureLocations) {
        ManyToOne manyToOne = ManyToOne.builder()
                .id("test many to one")
                .arrivalLocationId(arrivalLocation)
                .departureLocationIds(departureLocations)
                .transportation(DrivingAndPublicTransport.builder().build())
                .travelTime(900)
                .arrivalTimePeriod("weekday_morning")
                .properties(Arrays.asList(Property.TRAVEL_TIME, Property.FARES))
                .snapping(Snapping.builder()
                        .acceptRoads(Snapping.AcceptRoads.BOTH_DRIVABLE_AND_WALKABLE)
                        .penalty(Snapping.SnapPenalty.ENABLED)
                        .build())
                .build();

        return Collections.singletonList(manyToOne);
    }

    private List<OneToMany> createOneToMany(String departureLocation, List<String> arrivalLocations) {
        OneToMany oneToMany = OneToMany.builder()
                .id("test one to many")
                .departureLocationId(departureLocation)
                .arrivalLocationIds(arrivalLocations)
                .transportation(DrivingAndPublicTransport.builder().build())
                .travelTime(900)
                .arrivalTimePeriod("weekday_morning")
                .properties(Arrays.asList(Property.TRAVEL_TIME, Property.FARES))
                .snapping(Snapping.builder()
                        .acceptRoads(Snapping.AcceptRoads.ANY_DRIVABLE)
                        .penalty(Snapping.SnapPenalty.DISABLED)
                        .build())
                .build();

        return Collections.singletonList(oneToMany);
    }

    private List<DepartureSearch> createDepartureSearch(String departureLocation, List<String> arrivalLocations) {
        DepartureSearch ds = DepartureSearch.builder()
                .id("Test departure search")
                .departureLocationId(departureLocation)
                .arrivalLocationIds(arrivalLocations)
                .transportation(PublicTransport.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE))
                .range(new FullRange(true, 2, 300))
                .build();
        return Collections.singletonList(ds);
    }

    private List<ArrivalSearch> createArrivalSearch(List<String> departureLocations, String arrivalLocation) {
        ArrivalSearch as = ArrivalSearch.builder()
                .id("Test arrival search")
                .departureLocationIds(departureLocations)
                .arrivalLocationId(arrivalLocation)
                .transportation(PublicTransport.builder().build())
                .arrivalTime(Instant.now())
                .travelTime(900)
                .properties(Arrays.asList(Property.TRAVEL_TIME, Property.DISTANCE, Property.ROUTE, Property.FARES))
                .range(new FullRange(true, 1, 300))
                .build();
        return Collections.singletonList(as);
    }
}
