package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FastTrafficModel;
import com.traveltime.sdk.dto.common.transportationfast.Driving;
import com.traveltime.sdk.dto.common.transportationfast.DrivingAndPublicTransport;
import com.traveltime.sdk.dto.common.transportationfast.PublicTransport;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import com.traveltime.sdk.dto.requests.TimeMapFastBoxesRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastGeoJsonRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastRequest;
import com.traveltime.sdk.dto.requests.TimeMapFastWktRequest;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.requests.timemapfast.ArrivalSearches;
import com.traveltime.sdk.dto.requests.timemapfast.OneToMany;
import com.traveltime.sdk.dto.responses.TimeMapFastBoxesResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastGeoJsonResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastWktResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.timemapfast.Result;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
    public void shouldSendTransportationParams() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = DrivingAndPublicTransport.builder()
                .walkingTime(300)
                .drivingTimeToStation(600)
                .parkingTime(120)
                .build();

        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .build();

        Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldTrafficModelAffectResults() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);

        java.util.function.Function<FastTrafficModel, Integer> shellPoints = model -> {
            TimeMapFastRequest request = TimeMapFastRequest.builder()
                    .arrivalSearches(createArrivalSearches(
                            coords, Driving.builder().trafficModel(model).build()))
                    .build();
            Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
            Common.assertResponseIsRight(response);
            return response.get().getResults().stream()
                    .flatMap(result -> result.getShapes().stream())
                    .mapToInt(shape -> shape.getShell().size())
                    .sum();
        };

        Assert.assertNotEquals(shellPoints.apply(FastTrafficModel.PEAK), shellPoints.apply(FastTrafficModel.OFF_PEAK));
    }

    @Test
    public void shouldSendTimeMapFastRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .build();

        Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Assert.assertTrue(JsonUtils.isJsonValid(response.get()));
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastGeoJsonRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastGeoJsonRequest request = TimeMapFastGeoJsonRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .build();

        Either<TravelTimeError, TimeMapFastGeoJsonResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastBoundingBoxRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastBoxesRequest request = TimeMapFastBoxesRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .build();

        Either<TravelTimeError, TimeMapFastBoxesResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapFastWktRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapFastWktRequest request = TimeMapFastWktRequest.builder()
                .arrivalSearches(createArrivalSearches(coords, transportation))
                .withHoles(true)
                .build();

        Either<TravelTimeError, TimeMapFastWktResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendUnionAndIntersection() {
        Transportation transportation = new PublicTransport();
        OneToMany first = createOneToMany("first", new Coordinates(51.507609, -0.128315), transportation);
        OneToMany second = createOneToMany("second", new Coordinates(51.517609, -0.138315), transportation);

        TimeMapFastRequest request = TimeMapFastRequest.builder()
                .arrivalSearches(ArrivalSearches.builder()
                        .oneToMany(Arrays.asList(first, second))
                        .manyToOne(Collections.emptyList())
                        .build())
                .union(Union.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("union")
                        .build())
                .intersection(Intersection.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("intersection")
                        .build())
                .build();

        Either<TravelTimeError, TimeMapFastResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<String> searchIds =
                response.get().getResults().stream().map(Result::getSearchId).collect(Collectors.toList());
        Assert.assertTrue(searchIds.containsAll(Arrays.asList("first", "second", "union", "intersection")));
    }

    private ArrivalSearches createArrivalSearches(Coordinates coords, Transportation transportation) {
        OneToMany oneToMany = createOneToMany("Test arrival search fast", coords, transportation);

        return ArrivalSearches.builder()
                .oneToMany(Collections.singletonList(oneToMany))
                .manyToOne(Collections.emptyList())
                .build();
    }

    private OneToMany createOneToMany(String id, Coordinates coords, Transportation transportation) {
        return OneToMany.builder()
                .id(id)
                .arrivalTimePeriod("weekday_morning")
                .transportation(transportation)
                .coords(coords)
                .travelTime(900)
                .removeWaterBodies(true)
                .build();
    }
}
