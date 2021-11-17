package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TimeMapAsyncTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendAsyncTimeMapRequest() throws ExecutionException, InterruptedException {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = new PublicTransport();

        TimeMapRequest request = TimeMapRequest
            .builder()
            .arrivalSearches(createArrivalSearch(coords, transportation))
            .build();

        CompletableFuture<Either<TravelTimeError, TimeMapResponse>> response = sdk.sendAsync(request);
        Assert.assertTrue(response.get().isRight());
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coords, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test async arrival search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900
        );

        return Collections.singletonList(as);
    }
}
