package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterPostcodesRequest;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.postcodes.*;
import com.traveltime.sdk.dto.responses.TimeFilterPostcodesResponse;
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
import java.util.List;

public class TimeFilterPostcodesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendTimeFilterPostcodesRequest() throws IOException, RequestValidationException {
        Coordinates coordinates = new Coordinates(51.508930,-0.131387);
        Transportation transport = new PublicTransport();

        TimeFilterPostcodesRequest request = new TimeFilterPostcodesRequest(
            createDepartureSearch(coordinates, transport),
            createArrivalSearch(coordinates, transport)
        );

        TravelTimeResponse<TimeFilterPostcodesResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private List<DepartureSearch> createDepartureSearch(Coordinates coordinates, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            coordinates,
            transportation,
            Date.from(Instant.now()),
            900,
            Arrays.asList(Property.TRAVEL_TIME)
        );
        return Collections.singletonList(ds);
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coordinates, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test arrival search",
            coordinates,
            transportation,
            Date.from(Instant.now()),
            900,
            Arrays.asList(Property.TRAVEL_TIME),
            new FullRange(true, 1, 300)
        );
        return Collections.singletonList(as);
    }
}
