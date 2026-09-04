package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.PolygonsFilter;
import com.traveltime.sdk.dto.common.transportation.DrivingFerry;
import com.traveltime.sdk.dto.requests.DistanceMapRequest;
import com.traveltime.sdk.dto.requests.distancemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.distancemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.DistanceMapResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.time.Instant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DistanceMapTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendDistanceMapRequest() {
        DistanceMapRequest request = DistanceMapRequest.builder()
                .departureSearch(DepartureSearch.builder()
                        .id("departure")
                        .coords(new Coordinates(51.507609, -0.128315))
                        .transportation(DrivingFerry.builder().build())
                        .departureTime(Instant.now())
                        .travelDistance(3000)
                        .noHoles(true)
                        .removeWaterBodies(false)
                        .polygonsFilter(new PolygonsFilter(2))
                        .build())
                .arrivalSearch(ArrivalSearch.builder()
                        .id("arrival")
                        .coords(new Coordinates(51.507609, -0.128315))
                        .transportation(DrivingFerry.builder().build())
                        .arrivalTime(Instant.now())
                        .travelDistance(3000)
                        .build())
                .union(Union.builder()
                        .id("union")
                        .searchId("departure")
                        .searchId("arrival")
                        .build())
                .build();

        Either<TravelTimeError, DistanceMapResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
        Assert.assertEquals(3, response.get().getResults().size());
        response.get()
                .getResults()
                .forEach(result -> Assert.assertFalse(result.getShapes().isEmpty()));
    }
}
