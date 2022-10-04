package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TimeFilterProtoDistanceRequest;
import com.traveltime.sdk.dto.requests.protodistance.Country;
import com.traveltime.sdk.dto.requests.protodistance.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterProtoDistanceResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeFilterProtoDistanceTest {
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
    public void shouldSendProtoDistanceRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.425600, -0.122000));
        Country country = Country.UNITED_KINGDOM;
        Transportation transportation = Transportation.DRIVING_FERRY;
        TimeFilterProtoDistanceRequest request = new TimeFilterProtoDistanceRequest(origin, destinations, 3200, transportation, country);
        Either<TravelTimeError, TimeFilterProtoDistanceResponse> response = sdk.sendProto(request);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSplitProtoRequestsTest() {
        TimeFilterProtoDistanceRequest request = new TimeFilterProtoDistanceRequest(
            new Coordinates(51.425709, -0.122061),
            Common.generateCoordinates(12),
            7200,
            Transportation.DRIVING_FERRY,
            Country.UNITED_KINGDOM
        );

        List<ProtoRequest<TimeFilterProtoDistanceResponse>> requests = request.split(3);

        Assert.assertEquals(4, requests.size());
        Assert.assertTrue(requests.stream().allMatch(req -> req.getDestinationCoordinates().size() <= 3));
    }


    @Test
    public void shouldSplitProtoRequestsEvenlyTest() {
        TimeFilterProtoDistanceRequest request = new TimeFilterProtoDistanceRequest(
            new Coordinates(51.425709, -0.122061),
            Common.generateCoordinates(100),
            7200,
            Transportation.DRIVING_FERRY,
            Country.UNITED_KINGDOM
        );
        List<ProtoRequest<TimeFilterProtoDistanceResponse>> requests = request.split(100);
        Assert.assertTrue(requests.stream().allMatch(req -> req.getDestinationCoordinates().size() != 1));
    }

    @Test
    public void shouldSendBatchAsyncRequest() {
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
        TimeFilterProtoDistanceRequest request =  new TimeFilterProtoDistanceRequest(
            origin,
            destinations,
            7200,
            Transportation.DRIVING_FERRY,
            Country.UNITED_KINGDOM
        );
        val response = sdk.sendProtoAsyncBatched(request, 3).join();
        Assert.assertTrue(response.isRight());
        Assert.assertEquals(10, response.get().getTravelTimes().size());
    }
}
