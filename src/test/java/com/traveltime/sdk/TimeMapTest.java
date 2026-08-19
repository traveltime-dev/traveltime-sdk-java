package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.levelofdetail.Level;
import com.traveltime.sdk.dto.common.levelofdetail.SimpleLevelOfDetail;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.requests.*;
import com.traveltime.sdk.dto.requests.timemap.*;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.JsonUtils;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import org.junit.*;

public class TimeMapTest {
    TravelTimeSDK sdk;

    private final String arrSearchId = "Test arrival search";
    private final String depSearchId = "Test departure search";
    private final List<String> searchIds = Arrays.asList(arrSearchId, depSearchId);

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeMapRequest() {

        TimeMapRequest request = TimeMapRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .build();

        Either<TravelTimeError, TimeMapResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldReceiveValidJsonResponse() {

        TimeMapRequest request = TimeMapRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Assert.assertTrue(JsonUtils.isJsonValid(response.get()));
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapGeoJsonRequest() {

        TimeMapGeoJsonRequest request = TimeMapGeoJsonRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .build();

        Either<TravelTimeError, TimeMapGeoJsonResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapKMLRequest() {

        TimeMapKmlRequest request = TimeMapKmlRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .build();

        Either<TravelTimeError, Kml> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeMapBoundingBoxRequest() {

        TimeMapBoxesRequest request = TimeMapBoxesRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .build();

        Either<TravelTimeError, TimeMapBoxesResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendFullTimeMapWktRequest() {

        TimeMapWktRequest request = TimeMapWktRequest.builder()
                .departureSearch(createDepartureSearch())
                .arrivalSearch(createArrivalSearch())
                .intersection(createIntersection())
                .union(createUnion())
                .withHoles(true)
                .build();

        Either<TravelTimeError, TimeMapWktResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private ArrivalSearch createArrivalSearch() {
        return ArrivalSearch.builder()
                .id(arrSearchId)
                .coords(new Coordinates(51.507609, -0.128315))
                .transportation(PublicTransport.builder().build())
                .arrivalTime(Instant.now())
                .travelTime(900)
                .range(new Range(true, 400))
                .levelOfDetail(new SimpleLevelOfDetail(Level.MEDIUM))
                .singleShape(false)
                .noHoles(false)
                .removeWaterBodies(false)
                .build();
    }

    private DepartureSearch createDepartureSearch() {
        return DepartureSearch.builder()
                .id(depSearchId)
                .coords(new Coordinates(51.507609, -0.128315))
                .transportation(PublicTransport.builder().build())
                .departureTime(Instant.now())
                .travelTime(900)
                .range(new Range(true, 400))
                .levelOfDetail(new SimpleLevelOfDetail(Level.MEDIUM))
                .singleShape(false)
                .noHoles(false)
                .removeWaterBodies(true)
                .build();
    }

    private Union createUnion() {
        return new Union("union of driving and public transport", searchIds);
    }

    private Intersection createIntersection() {
        return new Intersection("intersection of driving and public transport", searchIds);
    }
}
