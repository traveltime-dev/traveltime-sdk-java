package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.Countries;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TimeFilterFastProtoTest {
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
    public void shouldSendTimeFilterProtoRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        TimeFilterFastProtoRequest request = oneToMany(origin, destinations);
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendManyToOneProtoRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        TimeFilterFastProtoRequest request = manyToOne(origin, destinations);
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReturnDistanceWhenFlagIsSpecified() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        TimeFilterFastProtoRequest request = new TimeFilterFastProtoRequest(
             origin,
             destinations,
             Transportation.Modes.DRIVING_FERRY,
             7200,
             Countries.UNITED_KINGDOM,
             RequestType.ONE_TO_MANY,
             true
        );
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);

        Assert.assertEquals(1, response.get().getDistances().size());
        Assert.assertEquals(1, response.get().getTravelTimes().size());
    }

    @Test
    public void shouldSplitProtoRequestsTest() {
        TimeFilterFastProtoRequest request = oneToMany(
                new Coordinates(51.425709, -0.122061),
                Common.generateCoordinates(12)
        );

        List<ProtoRequest<TimeFilterFastProtoResponse>> requests = request.split(3);

        Assert.assertEquals(4, requests.size());
        Assert.assertTrue(requests.stream().allMatch(req -> req.getDestinationCoordinates().size() <= 3));
    }


    @Test
    public void shouldSplitProtoRequestsEvenlyTest() {
        TimeFilterFastProtoRequest request = oneToMany(
                new Coordinates(51.425709, -0.122061),
                Common.generateCoordinates(1001)
        );

        List<ProtoRequest<TimeFilterFastProtoResponse>> requests = request.split(100);
        Assert.assertTrue(requests.stream().allMatch(req -> req.getDestinationCoordinates().size() != 1));
    }


    @Test
    public void shouldSendAsyncTimeFilterProtoRequest() throws ExecutionException, InterruptedException {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        TimeFilterFastProtoRequest request = oneToMany(origin, destinations);
        CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>> response = sdk.sendProtoAsync(request);
        Assert.assertTrue(response.get().isRight());
    }

    @Test
    public void shouldSendBatchTimeFilterProtoRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Arrays.asList(
                new Coordinates(51.348605, -0.314783),
                new Coordinates(51.348705, -0.314783),
                new Coordinates(51.348805, -0.314783),
                new Coordinates(51.348905, -0.314783),
                new Coordinates(51.348605, -0.314883),
                new Coordinates(51.348705, -0.314883),
                new Coordinates(51.348805, -0.314883),
                new Coordinates(51.348905, -0.314883),
                new Coordinates(51.348605, -0.314983),
                new Coordinates(51.348705, -0.314983)
        );
        TimeFilterFastProtoRequest request = oneToMany(origin, destinations);
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProtoBatched(request, 3);
        Common.assertResponseIsRight(response);
        Assert.assertEquals(10, response.get().getTravelTimes().size());
    }

    @Test
    public void shouldSendBatchAsyncTimeFilterProtoRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Arrays.asList(
                new Coordinates(51.348605, -0.314783),
                new Coordinates(51.348705, -0.314783),
                new Coordinates(51.348805, -0.314783),
                new Coordinates(51.348905, -0.314783),
                new Coordinates(51.348605, -0.314883),
                new Coordinates(51.348705, -0.314883),
                new Coordinates(51.348805, -0.314883),
                new Coordinates(51.348905, -0.314883),
                new Coordinates(51.348605, -0.314983),
                new Coordinates(51.348705, -0.314983)
        );
        TimeFilterFastProtoRequest request = oneToMany(origin, destinations);
        val response = sdk.sendProtoAsyncBatched(request, 3).join();
        Common.assertResponseIsRight(response);
        Assert.assertEquals(10, response.get().getTravelTimes().size());
    }

    @Test
    public void shouldSendMultipleAsyncTimeFilterProtoRequests() {
        List<Coordinates> coordinates = Arrays.asList(
                new Coordinates(51.348605, -0.314783),
                new Coordinates(51.344323, -0.324812),
                new Coordinates(51.334235, -0.321233),
                new Coordinates(51.323141, -0.324324),
                new Coordinates(51.323432, -0.324123),
                new Coordinates(51.312331, -0.324322),
                new Coordinates(51.323124, -0.312343)
        );
        List<CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>>> futures = Arrays.asList(
                sdk.sendProtoAsync(oneToMany(coordinates.get(0), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(1), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(2), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(3), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(4), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(5), coordinates)),
                sdk.sendProtoAsync(oneToMany(coordinates.get(6), coordinates))
        );

        boolean result = futures
                .stream()
                .map(CompletableFuture::join)
                .allMatch(Either::isRight);

        Assert.assertTrue(result);
    }

    @Test
    public void shouldSendRequestWithCustomTransportationDetails() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        Transportation.DrivingAndPublicTransport transportation = Transportation.DrivingAndPublicTransport
            .builder()
            .walkingTimeToStation(100)
            .drivingTimeToStation(100)
            .parkingTime(100)
            .build();

        TimeFilterFastProtoRequest request = new TimeFilterFastProtoRequest(
                origin,
                destinations,
                transportation,
                7200,
                Countries.UNITED_KINGDOM,
                RequestType.ONE_TO_MANY,
                true
        );
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);

        Assert.assertTrue(transportation.getWalkingTimeToStation() == 100);
        Assert.assertTrue(transportation.getDrivingTimeToStation() == 100);
        Assert.assertTrue(transportation.getParkingTime() == 100);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendRequestWhenCustomTransportationDetailsIsSetUsingWith() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        Transportation.PublicTransport transportation =
            Transportation.Modes.PUBLIC_TRANSPORT.withWalkingTimeToStation(100);

        TimeFilterFastProtoRequest request = new TimeFilterFastProtoRequest(
            origin,
            destinations,
            transportation,
            7200,
            Countries.UNITED_KINGDOM,
            RequestType.ONE_TO_MANY,
            true
        );
        Either<TravelTimeError, TimeFilterFastProtoResponse> response = sdk.sendProto(request);

        Assert.assertTrue(transportation.getWalkingTimeToStation() == 100);
        Assert.assertTrue(response.isRight());
    }

    public TimeFilterFastProtoRequest manyToOne(
            Coordinates origin,
            List<Coordinates> destinations
    ) {
        return new TimeFilterFastProtoRequest(
                origin,
                destinations,
                Transportation.Modes.PUBLIC_TRANSPORT,
                7200,
                Countries.UNITED_KINGDOM,
                RequestType.MANY_TO_ONE,
                false
        );
    }


    public TimeFilterFastProtoRequest oneToMany(
            Coordinates origin,
            List<Coordinates> destinations
    ) {
        return new TimeFilterFastProtoRequest(
                origin,
                destinations,
                Transportation.Modes.PUBLIC_TRANSPORT,
                7200,
                Countries.UNITED_KINGDOM,
                RequestType.ONE_TO_MANY,
                false
        );
    }
}
