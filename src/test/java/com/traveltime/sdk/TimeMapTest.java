package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.levelofdetail.Level;
import com.traveltime.sdk.dto.common.levelofdetail.SimpleLevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.*;
import com.traveltime.sdk.dto.requests.timemap.*;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.JsonUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TimeMapTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeMapRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapRequest request = new TimeMapRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds));

        Either<TravelTimeError, TimeMapResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapRequest request = new TimeMapRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds));

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Assert.assertTrue(JsonUtils.isJsonValid(response.get()));
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapGeoJsonRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapGeoJsonRequest request = new TimeMapGeoJsonRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds));

        Either<TravelTimeError, TimeMapGeoJsonResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapKMLRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapKmlRequest request = new TimeMapKmlRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds));

        Either<TravelTimeError, Kml> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapBoundingBoxRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapBoxesRequest request = new TimeMapBoxesRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds));

        Either<TravelTimeError, TimeMapBoxesResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendFullTimeMapWktRequest() {
        Coordinates coords = new Coordinates(51.507609, -0.128315);
        Transportation transportation = PublicTransport.builder().build();
        List<String> searchIds = Arrays.asList("Test arrival search", "Test departure search");

        TimeMapWktRequest request = new TimeMapWktRequest(
                createDepartureSearch(coords, transportation),
                createArrivalSearch(coords, transportation),
                createIntersection(searchIds),
                createUnion(searchIds),
                true);

        Either<TravelTimeError, TimeMapWktResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coords, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
                "Test arrival search",
                coords,
                transportation,
                Instant.now(),
                900,
                new Range(true, 400),
                new SimpleLevelOfDetail(Level.MEDIUM),
                false,
                false,
                null);

        return Collections.singletonList(as);
    }

    private List<DepartureSearch> createDepartureSearch(Coordinates coords, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
                "Test departure search",
                coords,
                transportation,
                Instant.now(),
                900,
                new Range(true, 400),
                new SimpleLevelOfDetail(Level.MEDIUM),
                false,
                false,
                null);
        return Collections.singletonList(ds);
    }

    private List<Union> createUnion(List<String> searchIds) {
        Union union = new Union("union of driving and public transport", searchIds);
        return Collections.singletonList(union);
    }

    private List<Intersection> createIntersection(List<String> searchIds) {
        Intersection intersection = new Intersection("intersection of driving and public transport", searchIds);
        return Collections.singletonList(intersection);
    }
}
