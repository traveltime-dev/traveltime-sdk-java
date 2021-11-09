package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.route.*;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.RoutesRequest;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.responses.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class JsonUtilsTest {
    @Test
    public void shouldParseAllJsonFiles() throws IOException {
        List<ImmutablePair<Class<Object>, String>> jsons = Arrays.asList(
            // requests
            new ImmutablePair(TimeFilterRequest.class, "dto/requests/timeFilterRequest.json"),
            new ImmutablePair(TimeMapRequest.class, "dto/requests/timeMapRequest.json"),
            new ImmutablePair(RoutesRequest.class, "dto/requests/routesRequest.json"),
            // responses
            new ImmutablePair(TimeMapResponse.class, "dto/responses/timeMapResponse.json"),
            new ImmutablePair(TimeMapWktResponse.class, "dto/responses/timeMapWktResponse.json"),
            new ImmutablePair(TimeMapBoxesResponse.class, "dto/responses/timeMapBoxesResponse.json"),
            new ImmutablePair(TimeFilterResponse.class, "dto/responses/timeFilterResponse.json"),
            new ImmutablePair(RoutesResponse.class, "dto/responses/routesResponse.json"),
            // transportations
            new ImmutablePair(Transportation.class, "dto/common/bus.json"),
            new ImmutablePair(Transportation.class, "dto/common/driving.json"),
            new ImmutablePair(Transportation.class, "dto/common/ferry.json"),
            new ImmutablePair(Transportation.class, "dto/common/walking.json"),
            new ImmutablePair(Transportation.class, "dto/common/coach.json"),
            new ImmutablePair(Transportation.class, "dto/common/publicTransport.json"),
            new ImmutablePair(Transportation.class, "dto/common/cycling.json"),
            new ImmutablePair(Transportation.class, "dto/common/train.json"),
            // routes
            new ImmutablePair(Part.class, "dto/common/routes/basic.json"),
            new ImmutablePair(StartEndPart.class, "dto/common/routes/start_end.json"),
            new ImmutablePair(RoadPart.class, "dto/common/routes/road.json"),
            new ImmutablePair(PublicTransportPart.class, "dto/common/routes/public_transport.json")
        );

        for(ImmutablePair<Class<Object>, String> json : jsons) {
            String expectedContent = Common.readFile(json.getValue());
            String resultContent = JsonUtils.toJsonPretty(JsonUtils.fromJson(expectedContent, json.getKey()));
            Assert.assertEquals(expectedContent, resultContent);
        }
    }
}
