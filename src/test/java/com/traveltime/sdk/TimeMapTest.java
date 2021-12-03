package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.*;
import com.traveltime.sdk.dto.requests.timemap.*;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.geojson.FeatureCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TimeMapTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendTimeMapRequest() {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapRequest request = new TimeMapRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds)
        );

        Either<TravelTimeError, TimeMapResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendTimeMapGeoJsonRequest() {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapGeoJsonRequest request = new TimeMapGeoJsonRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds)
        );

        Either<TravelTimeError, FeatureCollection> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendTimeMapBoundingBoxRequest() {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapBoxesRequest request = new TimeMapBoxesRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds)
        );

        Either<TravelTimeError, TimeMapBoxesResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendFullTimeMapWktRequest() {
        Coordinates coords = new Coordinates(51.507609,-0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapWktRequest request = new TimeMapWktRequest(
            createDepartureSearch(coords, transportation),
            createArrivalSearch(coords, transportation),
            createIntersection(searchIds),
            createUnion(searchIds),
            true
        );

        Either<TravelTimeError, TimeMapWktResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coords, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test arrival search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900,
            new Range(true, 400)
        );

        return Collections.singletonList(as);
    }

    private List<DepartureSearch> createDepartureSearch(Coordinates coords, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            coords,
            transportation,
            Date.from(Instant.now()),
            900,
            new Range(true, 400)
        );
        return Collections.singletonList(ds);
    }

    private List<Union> createUnion(List<String> searchIds) {
        Union union = new Union(
            "union of driving and public transport",
            searchIds
        );
        return Collections.singletonList(union);
    }

    private List<Intersection> createIntersection(List<String> searchIds) {
        Intersection intersection =  new Intersection(
            "intersection of driving and public transport",
            searchIds
        );
        return Collections.singletonList(intersection);
    }
}
