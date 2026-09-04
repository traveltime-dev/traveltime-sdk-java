package com.traveltime.sdk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.route.*;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.*;
import com.traveltime.sdk.dto.requests.TimeMapFastRequest;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.TimeMapFastResponse;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.utils.JsonUtils;
import java.io.IOException;
import java.util.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilsTest {
    @Test
    public void shouldParseAllJsonFiles() throws IOException {
        List<ImmutablePair<Class<Object>, String>> jsons = Arrays.asList(
                // requests
                new ImmutablePair(TimeFilterRequest.class, "dto/requests/timeFilterRequest.json"),
                new ImmutablePair(TimeMapRequest.class, "dto/requests/timeMapRequest.json"),
                new ImmutablePair(RoutesRequest.class, "dto/requests/routesRequest.json"),
                new ImmutablePair(SupportedLocationsRequest.class, "dto/requests/supportedLocationsRequest.json"),
                new ImmutablePair(TimeFilterFastRequest.class, "dto/requests/timeFilterFastRequest.json"),
                new ImmutablePair(TimeFilterSectorsRequest.class, "dto/requests/timeFilterSectorsRequest.json"),
                new ImmutablePair(TimeFilterDistrictsRequest.class, "dto/requests/timeFilterDistrictsRequest.json"),
                new ImmutablePair(TimeFilterPostcodesRequest.class, "dto/requests/timeFilterPostcodesRequest.json"),
                new ImmutablePair(H3Request.class, "dto/requests/h3Request.json"),
                new ImmutablePair(H3FastRequest.class, "dto/requests/h3FastRequest.json"),
                new ImmutablePair(GeohashRequest.class, "dto/requests/geohashRequest.json"),
                new ImmutablePair(GeohashFastRequest.class, "dto/requests/geohashFastRequest.json"),
                new ImmutablePair(TimeMapFastRequest.class, "dto/requests/timeMapFastRequest.json"),

                // responses
                new ImmutablePair(TimeMapResponse.class, "dto/responses/timeMapResponse.json"),
                new ImmutablePair(TimeMapWktResponse.class, "dto/responses/timeMapWktResponse.json"),
                new ImmutablePair(TimeMapBoxesResponse.class, "dto/responses/timeMapBoxesResponse.json"),
                new ImmutablePair(TimeFilterResponse.class, "dto/responses/timeFilterResponse.json"),
                new ImmutablePair(RoutesResponse.class, "dto/responses/routesResponse.json"),
                new ImmutablePair(SupportedLocationsResponse.class, "dto/responses/supportedLocationsResponse.json"),
                new ImmutablePair(TimeFilterFastResponse.class, "dto/responses/timeFilterFastResponse.json"),
                new ImmutablePair(TimeFilterDistrictsResponse.class, "dto/responses/timeFilterDistrictsResponse.json"),
                new ImmutablePair(TimeFilterSectorsResponse.class, "dto/responses/timeFilterSectorsResponse.json"),
                new ImmutablePair(TimeFilterPostcodesResponse.class, "dto/responses/timeFilterPostcodesResponse.json"),
                new ImmutablePair(MapInfoResponse.class, "dto/responses/mapInfoResponse.json"),
                new ImmutablePair(H3Response.class, "dto/responses/h3Response.json"),
                new ImmutablePair(GeohashResponse.class, "dto/responses/geohashResponse.json"),
                new ImmutablePair(ResponseError.class, "dto/responses/errorResponse.json"),
                new ImmutablePair(TimeMapFastResponse.class, "dto/responses/timeMapFastResponse.json"),
                new ImmutablePair(GeocodingResponse.class, "dto/responses/geocodingResponse.json"),

                // transportations
                new ImmutablePair(Transportation.class, "dto/common/bus.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingDefaults.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingExplicit.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingFerryDefaults.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingFerryExplicit.json"),
                new ImmutablePair(Transportation.class, "dto/common/ferry.json"),
                new ImmutablePair(Transportation.class, "dto/common/walking.json"),
                new ImmutablePair(Transportation.class, "dto/common/coach.json"),
                new ImmutablePair(Transportation.class, "dto/common/publicTransport.json"),
                new ImmutablePair(Transportation.class, "dto/common/cycling.json"),
                new ImmutablePair(Transportation.class, "dto/common/train.json"),
                new ImmutablePair(Transportation.class, "dto/common/cyclingFerry.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingPublicTransport.json"),
                new ImmutablePair(Transportation.class, "dto/common/cyclingPublicTransport.json"),
                new ImmutablePair(Transportation.class, "dto/common/drivingTrain.json"),
                // fast transportations
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/publicTransport.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/driving.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/drivingAndFerry.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/drivingAndPublicTransport.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/cycling.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/walking.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/walkingAndFerry.json"),
                new ImmutablePair(
                        com.traveltime.sdk.dto.common.transportationfast.Transportation.class,
                        "dto/common/fast/cyclingAndFerry.json"),

                // routes
                new ImmutablePair(Part.class, "dto/common/routes/basic.json"),
                new ImmutablePair(StartEndPart.class, "dto/common/routes/start_end.json"),
                new ImmutablePair(RoadPart.class, "dto/common/routes/road.json"),
                new ImmutablePair(PublicTransportPart.class, "dto/common/routes/public_transport.json"));

        for (ImmutablePair<Class<Object>, String> json : jsons) {
            String expectedContent = Common.readFile(json.getValue());
            String result = JsonUtils.toJsonPretty(
                            JsonUtils.fromJson(expectedContent, json.getKey()).get())
                    .get();
            Assert.assertEquals(expectedContent, result);
        }
    }

    @Test
    public void shouldParseListOfProperties() throws IOException {
        String expectedContent = Common.readFile("dto/common/properties.json");
        String result = JsonUtils.toJsonPretty(
                        JsonUtils.fromJson(expectedContent, new TypeReference<List<Property>>() {})
                                .get())
                .get();
        Assert.assertEquals(expectedContent, result);
    }
}
