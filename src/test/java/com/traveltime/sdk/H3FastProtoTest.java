package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.H3FastProtoRequest;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.proto.CellProperty;
import com.traveltime.sdk.dto.requests.proto.Countries;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.H3FastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class H3FastProtoTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    private H3FastProtoRequest buildRequest(RequestType requestType) {
        return H3FastProtoRequest.builder()
                .originCoordinate(new Coordinates(51.507609, -0.128315))
                .transportation(Transportation.Modes.DRIVING_FERRY)
                .travelTime(900)
                .resolution(7)
                .country(Countries.UNITED_KINGDOM)
                .requestType(requestType)
                .build();
    }

    private H3FastProtoRequest oneToMany() {
        return buildRequest(RequestType.ONE_TO_MANY);
    }

    @Test
    public void shouldSendH3ProtoRequest() {
        Either<TravelTimeError, H3FastProtoResponse> response = sdk.sendProto(oneToMany());
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldSendAsyncH3ProtoRequest() throws ExecutionException, InterruptedException {
        CompletableFuture<Either<TravelTimeError, H3FastProtoResponse>> response = sdk.sendProtoAsync(oneToMany());
        Assert.assertTrue(response.get().isRight());
    }

    @Test
    public void shouldSendManyToOneH3ProtoRequest() {
        Either<TravelTimeError, H3FastProtoResponse> response = sdk.sendProto(buildRequest(RequestType.MANY_TO_ONE));
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getIds().isEmpty());
    }

    @Test
    public void shouldReturnTravelTimesForEveryCell() {
        Either<TravelTimeError, H3FastProtoResponse> response = sdk.sendProto(oneToMany());
        Common.assertResponseIsRight(response);

        H3FastProtoResponse result = response.get();
        Assert.assertFalse(result.getMinTravelTimes().isEmpty());
        Assert.assertEquals(result.getIds().size(), result.getMinTravelTimes().size());
        Assert.assertEquals(result.getIds().size(), result.getMaxTravelTimes().size());
        Assert.assertEquals(result.getIds().size(), result.getMeanTravelTimes().size());

        for (String id : result.getIds()) {
            Assert.assertEquals(15, id.length());
            Assert.assertEquals(1L, (Long.parseUnsignedLong(id, 16) >>> 59) & 0xFL);
        }
    }

    @Test
    public void shouldReturnOnlyRequestedProperties() {
        H3FastProtoRequest request = oneToMany().withProperties(Collections.singletonList(CellProperty.MIN));
        Either<TravelTimeError, H3FastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);

        H3FastProtoResponse result = response.get();
        Assert.assertFalse(result.getMinTravelTimes().isEmpty());
        Assert.assertTrue(result.getMaxTravelTimes().isEmpty());
        Assert.assertTrue(result.getMeanTravelTimes().isEmpty());
    }

    @Test
    public void shouldReturnCellIdsOnlyWhenNoPropertiesRequested() {
        H3FastProtoRequest request = oneToMany().withProperties(Collections.emptyList());
        Either<TravelTimeError, H3FastProtoResponse> response = sdk.sendProto(request);
        Common.assertResponseIsRight(response);

        H3FastProtoResponse result = response.get();
        Assert.assertFalse(result.getIds().isEmpty());
        Assert.assertTrue(result.getMinTravelTimes().isEmpty());
        Assert.assertTrue(result.getMaxTravelTimes().isEmpty());
        Assert.assertTrue(result.getMeanTravelTimes().isEmpty());
    }

    @Test
    public void shouldNotSplitH3ProtoRequest() {
        List<ProtoRequest<H3FastProtoResponse>> requests = oneToMany().split(3);
        Assert.assertEquals(1, requests.size());
    }
}
