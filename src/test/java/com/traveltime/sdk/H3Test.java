package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.H3Request;
import com.traveltime.sdk.dto.requests.h3.ArrivalSearch;
import com.traveltime.sdk.dto.requests.h3.Coords;
import com.traveltime.sdk.dto.requests.h3.DepartureSearch;
import com.traveltime.sdk.dto.requests.h3.H3CentroidCoords;
import com.traveltime.sdk.dto.requests.h3.LatLngCoords;
import com.traveltime.sdk.dto.requests.h3.Property;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.H3Response;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.h3.Cell;
import com.traveltime.sdk.dto.responses.h3.Result;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class H3Test {
    private static final Coords TRAFALGAR_SQUARE = new LatLngCoords(51.507609, -0.128315);

    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    private DepartureSearch departureSearch(String id, Coords coords) {
        return DepartureSearch.builder()
                .id(id)
                .coords(coords)
                .transportation(Driving.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .build();
    }

    @Test
    public void shouldSendH3Request() {
        H3Request request = H3Request.builder()
                .resolution(8)
                .property(Property.MIN)
                .property(Property.MAX)
                .property(Property.MEAN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<Cell> cells = response.get().getResults().get(0).getCells();
        Assert.assertFalse(cells.isEmpty());
        Assert.assertNotNull(cells.get(0).getProperties().getMin());
        Assert.assertNotNull(cells.get(0).getProperties().getMax());
        Assert.assertNotNull(cells.get(0).getProperties().getMean());
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        H3Request request = H3Request.builder()
                .resolution(8)
                .property(Property.MIN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Common.assertResponseIsRight(response);
        Assert.assertTrue(JsonUtils.fromJson(response.get(), H3Response.class).isRight());
    }

    @Test
    public void shouldReturnOnlyRequestedProperties() {
        H3Request request = H3Request.builder()
                .resolution(8)
                .property(Property.MIN)
                .departureSearch(departureSearch("departure", TRAFALGAR_SQUARE))
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        Cell cell = response.get().getResults().get(0).getCells().get(0);
        Assert.assertNotNull(cell.getProperties().getMin());
        Assert.assertNull(cell.getProperties().getMax());
        Assert.assertNull(cell.getProperties().getMean());
    }

    @Test
    public void shouldSendArrivalSearchWithH3CentroidCoords() {
        ArrivalSearch arrivalSearch = ArrivalSearch.builder()
                .id("arrival")
                .coords(new H3CentroidCoords("87194ad14ffffff"))
                .transportation(Driving.builder().build())
                .arrivalTime(Instant.now())
                .travelTime(900)
                .build();

        H3Request request = H3Request.builder()
                .resolution(8)
                .property(Property.MEAN)
                .arrivalSearch(arrivalSearch)
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getResults().get(0).getCells().isEmpty());
    }

    @Test
    public void shouldSendUnionAndIntersection() {
        H3Request request = H3Request.builder()
                .resolution(8)
                .property(Property.MIN)
                .departureSearch(departureSearch("first", TRAFALGAR_SQUARE))
                .departureSearch(departureSearch("second", new LatLngCoords(51.517609, -0.128315)))
                .union(Union.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("union")
                        .build())
                .intersection(Intersection.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("intersection")
                        .build())
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<String> searchIds =
                response.get().getResults().stream().map(Result::getSearchId).collect(Collectors.toList());
        Assert.assertTrue(searchIds.containsAll(Arrays.asList("first", "second", "union", "intersection")));
    }
}
