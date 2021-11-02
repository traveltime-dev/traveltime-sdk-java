package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TravelTimeResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class IntegrationTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        System.out.println(System.getenv());
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendTimeFilterRequest() throws IOException, RequestValidationException {
        String requestJson = Common.readFile("dto/requests/timeFilterRequest.json");
        TimeFilterRequest timeFilterRequest = JsonUtils.fromJson(requestJson, TimeFilterRequest.class);
        TravelTimeResponse<TimeFilterResponse> timeFilterResponse = sdk.send(timeFilterRequest);

        Assert.assertEquals(200, (int) timeFilterResponse.getHttpCode());
        Assert.assertNotNull(timeFilterResponse.getParsedBody());
    }

    @Test
    public void shouldSendTimeMapRequest() throws IOException, RequestValidationException {
        String requestJson = Common.readFile("dto/requests/timeMapRequest.json");
        TimeMapRequest timeMapRequest = JsonUtils.fromJson(requestJson, TimeMapRequest.class);
        TravelTimeResponse<TimeMapResponse> timeMapResponse = sdk.send(timeMapRequest);

        Assert.assertEquals(200, (int) timeMapResponse.getHttpCode());
        Assert.assertNotNull(timeMapResponse.getParsedBody());
    }

    @Test
    public void shouldSendAsyncTimeMapRequest() throws ExecutionException, InterruptedException, IOException, RequestValidationException {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = new PublicTransport();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapRequest timeMapRequest = new TimeMapRequest(
            Collections.singletonList(createDepartureSearch(coords, transportation)),
            Collections.singletonList(createArrivalSearch(coords, transportation)),
            Collections.singletonList(createIntersection(searchIds)),
            Collections.singletonList(createUnion(searchIds))
        );

        CompletableFuture<TravelTimeResponse<TimeMapResponse>> responseFuture = sdk.sendAsync(timeMapRequest);
        TravelTimeResponse<TimeMapResponse> response = responseFuture.get();

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private ArrivalSearch createArrivalSearch(Coordinates coords, Transportation transportation) {
        return new ArrivalSearch(
            "Test arrival search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900
        );
    }

    private DepartureSearch createDepartureSearch(Coordinates coords, Transportation transportation) {
        return new DepartureSearch(
            "Test departure search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900
        );
    }

    private Union createUnion(Iterable<String> searchIds) {
        return new Union(
            "union of driving and public transport",
            searchIds
        );
    }

    private Intersection createIntersection(Iterable<String> searchIds) {
        return new Intersection(
            "intersection of driving and public transport",
            searchIds
        );
    }


}
