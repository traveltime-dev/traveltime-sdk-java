package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportationfast.PublicTransport;
import com.traveltime.sdk.dto.requests.TimeMapFastBoxesRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastGeoJsonRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastWktRequest;
import com.traveltime.sdk.dto.requests.timemapfast.*;
import com.traveltime.sdk.dto.responses.TimeMapFastBoxesResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastGeoJsonResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeMapFastTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeMapFastRequest() {
        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(createArrivalSearches())
                .build();

        Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(createArrivalSearches())
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Assert.assertTrue(JsonUtils.isJsonValid(response.get()));
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastGeoJsonRequest() {
        TimeMapFastGeoJsonRequest request = TimeMapFastGeoJsonRequest.builder()
                .arrivalSearches(createArrivalSearches())
                .build();

        Either<TravelTimeError, TimeMapFastGeoJsonResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastBoundingBoxRequest() {
        TimeMapFastBoxesRequest request = TimeMapFastBoxesRequest.builder()
                .arrivalSearches(createArrivalSearches())
                .build();

        Either<TravelTimeError, TimeMapFastBoxesResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastWktRequest() {
        TimeMapFastWktRequest request = TimeMapFastWktRequest.builder()
                .arrivalSearches(createArrivalSearches())
                .withHoles(true)
                .build();

        Either<TravelTimeError, TimeMapFastWktResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private ArrivalSearches createArrivalSearches() {
        Search oneToMany = Search.builder()
                .id("Test arrival search fast")
                .arrivalTimePeriod("weekday_morning")
                .transportation(new PublicTransport())
                .coords(new Coordinates(51.507609, -0.128315))
                .travelTime(900)
                .removeWaterBodies(true)
                .build();

        return ArrivalSearches.builder()
                .oneToMany(Collections.singletonList(oneToMany))
                .manyToOne(Collections.emptyList())
                .build();
    }
}
