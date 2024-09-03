package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterPostcodesRequest;
import com.traveltime.sdk.dto.requests.postcodes.*;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class TimeFilterPostcodesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials = new TravelTimeCredentials(
            System.getenv("APP_ID"),
            System.getenv("API_KEY")
        );
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeFilterPostcodesRequest() {
        Coordinates coordinates = new Coordinates(51.508930,-0.131387);
        Transportation transport = PublicTransport.builder().build();

        TimeFilterPostcodesRequest request = new TimeFilterPostcodesRequest(
            createDepartureSearch(coordinates, transport),
            createArrivalSearch(coordinates, transport)
        );

        Either<TravelTimeError, TimeFilterPostcodesResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    private List<DepartureSearch> createDepartureSearch(Coordinates coordinates, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            coordinates,
            transportation,
            Instant.now(),
            900,
            Collections.singletonList(Property.TRAVEL_TIME),
            new FullRange(true, 1, 300),
            null
        );
        return Collections.singletonList(ds);
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coordinates, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test arrival search",
            coordinates,
            transportation,
            Instant.now(),
            900,
            Collections.singletonList(Property.TRAVEL_TIME),
            new FullRange(true, 1, 300),
            null
        );
        return Collections.singletonList(as);
    }
}
