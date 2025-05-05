package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportationfast.PublicTransport;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import com.traveltime.sdk.dto.requests.TimeMapFastBoxesRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastGeoJsonRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastWktRequest;
import com.traveltime.sdk.dto.requests.timemapfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timemapfast.OneToMany;
import com.traveltime.sdk.dto.responses.TimeMapFastBoxesResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastGeoJsonResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class TimeMapFastTest {
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
    public void shouldSendTimeMapFastRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastRequest request = new TimeMapFastRequest(
                createArrivalSearches(coords, transportation)
        );

        Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastRequest request = new TimeMapFastRequest(
                createArrivalSearches(coords, transportation)
        );

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Assert.assertTrue(JsonUtils.isJsonValid(response.get()));
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastGeoJsonRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastGeoJsonRequest request = new TimeMapFastGeoJsonRequest(
                createArrivalSearches(coords, transportation)
        );

        Either<TravelTimeError, TimeMapFastGeoJsonResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastBoundingBoxRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastBoxesRequest request = new TimeMapFastBoxesRequest(
                createArrivalSearches(coords, transportation)
        );

        Either<TravelTimeError, TimeMapFastBoxesResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastWktRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastWktRequest request = new TimeMapFastWktRequest(
                createArrivalSearches(coords, transportation), true
        );

        Either<TravelTimeError, TimeMapFastWktResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private ArrivalSearches createArrivalSearches(Coordinates coords, Transportation transportation) {
        OneToMany oneToMany = OneToMany
                .builder()
                .id("Test arrival search fast")
                .arrivalTimePeriod("weekday_morning")
                .transportation(transportation)
                .coords(coords)
                .travelTime(900)
                .build();

        return ArrivalSearches
                .builder()
                .oneToMany(Collections.singletonList(oneToMany))
                .manyToOne(Collections.emptyList())
                .build();
    }
}
