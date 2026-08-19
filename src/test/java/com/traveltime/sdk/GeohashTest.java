package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.GeohashCentroidCoords;
import com.traveltime.sdk.dto.common.GeohashCoords;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.GeohashRequest;
import com.traveltime.sdk.dto.requests.cell.Property;
import com.traveltime.sdk.dto.requests.geohash.ArrivalSearch;
import com.traveltime.sdk.dto.requests.geohash.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.GeohashResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.geohash.Cell;
import com.traveltime.sdk.dto.responses.geohash.Result;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeohashTest {
    private static final Coordinates TRAFALGAR_SQUARE = new Coordinates(51.507609, -0.128315);

    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    private DepartureSearch departureSearch(String id, GeohashCoords coords) {
        return DepartureSearch.builder()
                .id(id)
                .coords(coords)
                .transportation(Driving.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .build();
    }

    @Test
    public void shouldSendGeohashRequest() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(6)
                .property(Property.MIN)
                .property(Property.MAX)
                .property(Property.MEAN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, GeohashResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<Cell> cells = response.get().getResults().get(0).getCells();
        Assert.assertFalse(cells.isEmpty());
        Assert.assertNotNull(cells.get(0).getProperties().getMin());
        Assert.assertNotNull(cells.get(0).getProperties().getMax());
        Assert.assertNotNull(cells.get(0).getProperties().getMean());
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(6)
                .property(Property.MIN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Common.assertResponseIsRight(response);
        Assert.assertTrue(
                JsonUtils.fromJson(response.get(), GeohashResponse.class).isRight());
    }

    @Test
    public void shouldReturnOnlyRequestedProperties() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(6)
                .property(Property.MIN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, GeohashResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        Cell cell = response.get().getResults().get(0).getCells().get(0);
        Assert.assertNotNull(cell.getProperties().getMin());
        Assert.assertNull(cell.getProperties().getMax());
        Assert.assertNull(cell.getProperties().getMean());
    }

    @Test
    public void shouldSendArrivalSearchWithGeohashCentroidCoords() {
        ArrivalSearch arrivalSearch = ArrivalSearch.builder()
                .id("arrival")
                .coords(new GeohashCentroidCoords("gcpuv5"))
                .transportation(Driving.builder().build())
                .arrivalTime(Instant.now())
                .travelTime(900)
                .build();

        GeohashRequest request = GeohashRequest.builder()
                .resolution(6)
                .property(Property.MEAN)
                .arrivalSearch(arrivalSearch)
                .build();

        Either<TravelTimeError, GeohashResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getResults().get(0).getCells().isEmpty());
    }

    @Test
    public void shouldSendUnionAndIntersection() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(6)
                .property(Property.MIN)
                .departureSearch(departureSearch("first", TRAFALGAR_SQUARE))
                .departureSearch(departureSearch("second", new Coordinates(51.517609, -0.128315)))
                .union(Union.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("union")
                        .build())
                .intersection(Intersection.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("intersection")
                        .build())
                .build();

        Either<TravelTimeError, GeohashResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<String> searchIds =
                response.get().getResults().stream().map(Result::getSearchId).collect(Collectors.toList());
        Assert.assertTrue(searchIds.containsAll(Arrays.asList("first", "second", "union", "intersection")));
    }
}
