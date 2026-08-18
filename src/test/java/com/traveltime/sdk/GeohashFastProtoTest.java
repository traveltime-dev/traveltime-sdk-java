package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.GeohashFastProtoRequest;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.proto.CellProperty;
import com.traveltime.sdk.dto.requests.proto.Countries;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.GeohashFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeohashFastProtoTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendGeohashProtoRequest() {
        GeohashFastProtoRequest request = oneToMany();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldSendAsyncGeohashProtoRequest() throws ExecutionException, InterruptedException {
        GeohashFastProtoRequest request = oneToMany();
        CompletableFuture<Either<TravelTimeError, GeohashFastProtoResponse>> response = sdk.sendProtoAsync(request);
        Assert.assertTrue(response.get().isRight());
    }

    @Test
    public void shouldSendManyToOneGeohashProtoRequest() {
        GeohashFastProtoRequest request = manyToOne();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldReturnTravelTimesInResponse() {
        GeohashFastProtoRequest request = oneToMany();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);

        GeohashFastProtoResponse result = response.get();
        Assert.assertFalse(result.getIds().isEmpty());
        Assert.assertFalse(result.getMinTravelTimes().isEmpty());
        Assert.assertFalse(result.getMaxTravelTimes().isEmpty());
        Assert.assertFalse(result.getMeanTravelTimes().isEmpty());
        Assert.assertEquals(result.getIds().size(), result.getMinTravelTimes().size());
        Assert.assertEquals(result.getIds().size(), result.getMaxTravelTimes().size());
        Assert.assertEquals(result.getIds().size(), result.getMeanTravelTimes().size());
    }

    @Test
    public void shouldReturnOnlyRequestedProperties() {
        GeohashFastProtoRequest request = oneToMany().withProperties(Collections.singletonList(CellProperty.MIN));
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);

        GeohashFastProtoResponse result = response.get();
        Assert.assertFalse(result.getMinTravelTimes().isEmpty());
        Assert.assertTrue(result.getMaxTravelTimes().isEmpty());
        Assert.assertTrue(result.getMeanTravelTimes().isEmpty());
    }

    /** The fast endpoint stops at 7, below the 9 the regular geohash endpoint allows. */
    @Test
    public void shouldRejectResolutionOutsideRange() {
        Either<TravelTimeError, GeohashFastProtoResponse> response =
                sdk.sendProto(oneToMany().withResolution(9));
        Assert.assertTrue("must not reach the API", response.isLeft());
        Assert.assertTrue(
                "must fail validation: " + response.getLeft().getClass().getSimpleName(),
                response.getLeft() instanceof ValidationError);
        Assert.assertEquals(
                "resolution should be between 4 and 7", response.getLeft().getMessage());
    }

    @Test
    public void shouldNotSplitGeohashProtoRequest() {
        GeohashFastProtoRequest request = oneToMany();
        List<ProtoRequest<GeohashFastProtoResponse>> requests = request.split(3);
        Assert.assertEquals(1, requests.size());
    }

    @Test
    public void shouldSendBatchGeohashProtoRequest() {
        GeohashFastProtoRequest request = oneToMany();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProtoBatched(request, 3);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldSendBatchAsyncGeohashProtoRequest() {
        GeohashFastProtoRequest request = oneToMany();
        val response = sdk.sendProtoAsyncBatched(request, 3).join();
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldSendMultipleAsyncGeohashProtoRequests() {
        List<Coordinates> origins = Arrays.asList(
                new Coordinates(51.348605, -0.314783),
                new Coordinates(51.344323, -0.324812),
                new Coordinates(51.334235, -0.321233));
        List<CompletableFuture<Either<TravelTimeError, GeohashFastProtoResponse>>> futures = Arrays.asList(
                sdk.sendProtoAsync(buildRequest(origins.get(0), RequestType.ONE_TO_MANY)),
                sdk.sendProtoAsync(buildRequest(origins.get(1), RequestType.ONE_TO_MANY)),
                sdk.sendProtoAsync(buildRequest(origins.get(2), RequestType.ONE_TO_MANY)));

        boolean result = futures.stream().map(CompletableFuture::join).allMatch(Either::isRight);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldSendRequestWithCustomTransportationDetails() {
        Transportation.DrivingAndPublicTransport transportation = Transportation.DrivingAndPublicTransport.builder()
                .walkingTimeToStation(100)
                .drivingTimeToStation(100)
                .parkingTime(100)
                .build();

        GeohashFastProtoRequest request = GeohashFastProtoRequest.builder()
                .originCoordinate(new Coordinates(51.425709, -0.122061))
                .transportation(transportation)
                .travelTime(1800)
                .resolution(6)
                .country(Countries.UNITED_KINGDOM)
                .requestType(RequestType.ONE_TO_MANY)
                .build();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);

        Assert.assertTrue(transportation.getWalkingTimeToStation() == 100);
        Assert.assertTrue(transportation.getDrivingTimeToStation() == 100);
        Assert.assertTrue(transportation.getParkingTime() == 100);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendRequestWhenCustomTransportationDetailsIsSetUsingWith() {
        Transportation.PublicTransport transportation =
                Transportation.Modes.PUBLIC_TRANSPORT.withWalkingTimeToStation(100);

        GeohashFastProtoRequest request = GeohashFastProtoRequest.builder()
                .originCoordinate(new Coordinates(51.425709, -0.122061))
                .transportation(transportation)
                .travelTime(1800)
                .resolution(6)
                .country(Countries.UNITED_KINGDOM)
                .requestType(RequestType.ONE_TO_MANY)
                .build();
        Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(request);

        Assert.assertTrue(transportation.getWalkingTimeToStation() == 100);
        Assert.assertTrue(response.isRight());
    }

    public GeohashFastProtoRequest oneToMany() {
        return buildRequest(new Coordinates(51.425709, -0.122061), RequestType.ONE_TO_MANY);
    }

    public GeohashFastProtoRequest manyToOne() {
        return buildRequest(new Coordinates(51.425709, -0.122061), RequestType.MANY_TO_ONE);
    }

    private GeohashFastProtoRequest buildRequest(Coordinates origin, RequestType requestType) {
        return GeohashFastProtoRequest.builder()
                .originCoordinate(origin)
                .transportation(Transportation.Modes.DRIVING_FERRY)
                .travelTime(900)
                .resolution(6)
                .country(Countries.UNITED_KINGDOM)
                .requestType(requestType)
                .removeWaterBodies(false)
                .build();
    }
}
