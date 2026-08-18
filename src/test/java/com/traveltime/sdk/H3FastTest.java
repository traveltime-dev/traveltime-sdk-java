package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.transportationfast.DrivingAndFerry;
import com.traveltime.sdk.dto.requests.H3FastRequest;
import com.traveltime.sdk.dto.requests.h3.ArrivalSearches;
import com.traveltime.sdk.dto.requests.h3.Coords;
import com.traveltime.sdk.dto.requests.h3.FastSearch;
import com.traveltime.sdk.dto.requests.h3.LatLngCoords;
import com.traveltime.sdk.dto.requests.h3.Property;
import com.traveltime.sdk.dto.requests.timemap.Intersection;
import com.traveltime.sdk.dto.requests.timemap.Union;
import com.traveltime.sdk.dto.responses.H3Response;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.h3.Result;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class H3FastTest {
    private static final Coords TRAFALGAR_SQUARE = new LatLngCoords(51.507609, -0.128315);

    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    private FastSearch fastSearch(String id, Coords coords) {
        return FastSearch.builder()
                .id(id)
                .coords(coords)
                .transportation(new DrivingAndFerry())
                .arrivalTimePeriod("weekday_morning")
                .travelTime(900)
                .build();
    }

    @Test
    public void shouldSendOneToManyRequest() {
        H3FastRequest request = H3FastRequest.builder()
                .resolution(8)
                .property(Property.MIN)
                .property(Property.MAX)
                .property(Property.MEAN)
                .arrivalSearches(ArrivalSearches.builder()
                        .oneToMany(Collections.singletonList(fastSearch("one to many", TRAFALGAR_SQUARE)))
                        .manyToOne(Collections.emptyList())
                        .build())
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getResults().get(0).getCells().isEmpty());
    }

    @Test
    public void shouldSendManyToOneRequest() {
        H3FastRequest request = H3FastRequest.builder()
                .resolution(8)
                .property(Property.MEAN)
                .arrivalSearches(ArrivalSearches.builder()
                        .oneToMany(Collections.emptyList())
                        .manyToOne(Collections.singletonList(fastSearch("many to one", TRAFALGAR_SQUARE)))
                        .build())
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);
        Assert.assertFalse(response.get().getResults().get(0).getCells().isEmpty());
    }

    @Test
    public void shouldReceiveValidJsonResponse() {
        H3FastRequest request = H3FastRequest.builder()
                .resolution(8)
                .property(Property.MIN)
                .arrivalSearches(ArrivalSearches.builder()
                        .oneToMany(Collections.singletonList(fastSearch("one to many", TRAFALGAR_SQUARE)))
                        .manyToOne(Collections.emptyList())
                        .build())
                .build();

        Either<TravelTimeError, String> response = sdk.getJsonResponse(request);
        Common.assertResponseIsRight(response);
        Assert.assertTrue(JsonUtils.fromJson(response.get(), H3Response.class).isRight());
    }

    @Test
    public void shouldSendUnionAndIntersection() {
        H3FastRequest request = H3FastRequest.builder()
                .resolution(8)
                .property(Property.MIN)
                .arrivalSearches(ArrivalSearches.builder()
                        .oneToMany(Arrays.asList(
                                fastSearch("first", TRAFALGAR_SQUARE),
                                fastSearch("second", new LatLngCoords(51.517609, -0.128315))))
                        .manyToOne(Collections.emptyList())
                        .build())
                .union(Union.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("union")
                        .build())
                .intersection(Intersection.builder()
                        .searchIds(Arrays.asList("first", "second"))
                        .id("intersection")
                        .build())
                .build();

        Either<TravelTimeError, H3Response> response = sdk.send(request);
        Common.assertResponseIsRight(response);

        List<String> searchIds =
                response.get().getResults().stream().map(Result::getSearchId).collect(Collectors.toList());
        Assert.assertTrue(searchIds.containsAll(Arrays.asList("first", "second", "union", "intersection")));
    }
}
