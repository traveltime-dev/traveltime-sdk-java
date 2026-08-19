package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.GeohashFastRequest;
import com.traveltime.sdk.dto.requests.GeohashRequest;
import com.traveltime.sdk.dto.requests.H3Request;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResolutionValidationTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(new TravelTimeCredentials("id", "key"));
    }

    private com.traveltime.sdk.dto.requests.h3.DepartureSearch h3Search() {
        return com.traveltime.sdk.dto.requests.h3.DepartureSearch.builder()
                .id("d")
                .coords(new com.traveltime.sdk.dto.common.Coordinates(51.507609, -0.128315))
                .transportation(Driving.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .build();
    }

    private com.traveltime.sdk.dto.requests.geohash.DepartureSearch geohashSearch() {
        return com.traveltime.sdk.dto.requests.geohash.DepartureSearch.builder()
                .id("d")
                .coords(new com.traveltime.sdk.dto.common.Coordinates(51.507609, -0.128315))
                .transportation(Driving.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .build();
    }

    private void assertRejected(Either<TravelTimeError, ?> response, String expected) {
        Assert.assertTrue("request should not reach the API", response.isLeft());
        Assert.assertTrue(
                "must fail validation rather than reach the network: "
                        + response.getLeft().getClass().getSimpleName(),
                response.getLeft() instanceof ValidationError);
        Assert.assertTrue(
                "unexpected message: " + response.getLeft().getMessage(),
                response.getLeft().getMessage().contains(expected));
    }

    private com.traveltime.sdk.dto.requests.H3FastRequest h3Fast(int resolution) {
        return com.traveltime.sdk.dto.requests.H3FastRequest.builder()
                .resolution(resolution)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .arrivalSearches(com.traveltime.sdk.dto.requests.h3.ArrivalSearches.builder()
                        .oneToMany(Collections.emptyList())
                        .manyToOne(Collections.emptyList())
                        .build())
                .build();
    }

    @Test
    public void shouldRejectH3ResolutionAboveRange() {
        H3Request request = H3Request.builder()
                .resolution(13)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .departureSearch(h3Search())
                .build();

        assertRejected(sdk.send(request), "resolution should be between 4 and 12");
    }

    @Test
    public void shouldRejectH3ResolutionBelowRange() {
        H3Request request = H3Request.builder()
                .resolution(3)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .departureSearch(h3Search())
                .build();

        assertRejected(sdk.send(request), "resolution should be between 4 and 12");
    }

    @Test
    public void shouldRejectH3FastResolutionOutsideRange() {
        assertRejected(sdk.send(h3Fast(3)), "resolution should be between 4 and 12");
        assertRejected(sdk.send(h3Fast(13)), "resolution should be between 4 and 12");
    }

    @Test
    public void shouldRejectGeohashResolutionBelowRange() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(3)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .departureSearch(geohashSearch())
                .build();

        assertRejected(sdk.send(request), "resolution should be between 4 and 9");
    }

    @Test
    public void shouldRejectGeohashResolutionAboveRange() {
        GeohashRequest request = GeohashRequest.builder()
                .resolution(10)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .departureSearch(geohashSearch())
                .build();

        assertRejected(sdk.send(request), "resolution should be between 4 and 9");
    }

    /** The fast endpoint stops at 7, below the 9 the regular geohash endpoint allows. */
    @Test
    public void shouldRejectGeohashFastResolutionAllowedByRegularEndpoint() {
        GeohashFastRequest request = GeohashFastRequest.builder()
                .resolution(9)
                .property(com.traveltime.sdk.dto.requests.cell.Property.MIN)
                .arrivalSearches(com.traveltime.sdk.dto.requests.geohash.ArrivalSearches.builder()
                        .oneToMany(Collections.emptyList())
                        .manyToOne(Collections.emptyList())
                        .build())
                .build();

        assertRejected(sdk.send(request), "resolution should be between 4 and 7");
    }
}
