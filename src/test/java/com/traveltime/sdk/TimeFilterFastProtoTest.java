package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

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
        Assert.assertTrue(response.isRight());
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
        Assert.assertTrue(response.isRight());
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
        Assert.assertTrue(response.isRight());
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
        CompletableFuture<Either<TravelTimeError, TimeFilterFastProtoResponse>>[] futures = new CompletableFuture[] {
            sdk.sendProtoAsync(oneToMany(coordinates.get(0), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(1), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(2), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(3), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(4), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(5), coordinates)),
            sdk.sendProtoAsync(oneToMany(coordinates.get(6), coordinates)),

        };

        boolean result = Stream
            .of(futures)
            .map(CompletableFuture::join)
            .allMatch(Either::isRight);

        Assert.assertTrue(result);
    }

    public TimeFilterFastProtoRequest oneToMany(
        Coordinates origin,
        List<Coordinates> destinations
    ) {
        OneToMany oneToMany = new OneToMany(
            origin,
            destinations,
            Transportation.PUBLIC_TRANSPORT,
            7200,
            Country.NETHERLANDS
        );

        return new TimeFilterFastProtoRequest(oneToMany);
    }
}
