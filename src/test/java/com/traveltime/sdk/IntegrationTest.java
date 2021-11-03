package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapGeoJsonRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.requests.TimeMapWktRequest;
import com.traveltime.sdk.dto.requests.timemap.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.traveltime.sdk.dto.responses.TravelTimeResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import com.traveltime.sdk.exceptions.RequestValidationException;
import org.geojson.FeatureCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.sql.Time;
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
    public void shouldSendFullTimeMapRequest() throws ExecutionException, InterruptedException, IOException, RequestValidationException {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = new PublicTransport();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapRequest timeMapRequest = new TimeMapRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds)
        );

        TravelTimeResponse<TimeMapResponse> response = sdk.send(timeMapRequest);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    @Test
    public void shouldSendFullTimeMapGeoJsonRequest() throws IOException, RequestValidationException {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = new PublicTransport();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapGeoJsonRequest geoJsonRequest = new TimeMapGeoJsonRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds)
        );

        TravelTimeResponse<FeatureCollection> response = sdk.send(geoJsonRequest);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    @Test
    public void shouldSendFullTimeMapWktRequest() throws IOException, RequestValidationException {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = new PublicTransport();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapWktRequest timeMapWktRequest = new TimeMapWktRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds),
            true
        );

        TravelTimeResponse<TimeMapWktResponse> response = sdk.send(timeMapWktRequest);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private Iterable<ArrivalSearch> createArrivalSearch(Coordinates coords, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test arrival search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900
        );

        return Collections.singletonList(as);
    }

    private Iterable<DepartureSearch> createDepartureSearch(Coordinates coords, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900
        );
        return Collections.singletonList(ds);
    }

    private Iterable<Union> createUnion(Iterable<String> searchIds) {
        Union union = new Union(
            "union of driving and public transport",
            searchIds
        );
        return Collections.singletonList(union);
    }

    private Iterable<Intersection> createIntersection(Iterable<String> searchIds) {
        Intersection intersection =  new Intersection(
            "intersection of driving and public transport",
            searchIds
        );
        return Collections.singletonList(intersection);
    }
}
