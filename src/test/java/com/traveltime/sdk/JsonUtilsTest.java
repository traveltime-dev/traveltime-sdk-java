package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapBoxesResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.vividsolutions.jts.io.ParseException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class JsonUtilsTest {
    @Test
    public void shouldParseAllJsonFiles() throws IOException, ParseException, org.json.simple.parser.ParseException {
        List<ImmutablePair<Class<Object>, String>> jsons = Arrays.asList(
            // requests
            new ImmutablePair(TimeFilterRequest.class, "dto/requests/timeFilterRequest.json"),
            new ImmutablePair(TimeMapRequest.class, "dto/requests/timeMapRequest.json"),
            // responses
            new ImmutablePair(TimeMapResponse.class, "dto/responses/timeMapResponse.json"),
            new ImmutablePair(TimeMapWktResponse.class, "dto/responses/timeMapWktResponse.json"),
            new ImmutablePair(TimeMapBoxesResponse.class, "dto/responses/timeMapBoxesResponse.json"),
            new ImmutablePair(TimeFilterResponse.class, "dto/responses/timeFilterResponse.json"),
            // transportations
            new ImmutablePair(Transportation.class, "dto/common/bus.json"),
            new ImmutablePair(Transportation.class, "dto/common/driving.json"),
            new ImmutablePair(Transportation.class, "dto/common/ferry.json"),
            new ImmutablePair(Transportation.class, "dto/common/walking.json"),
            new ImmutablePair(Transportation.class, "dto/common/coach.json"),
            new ImmutablePair(Transportation.class, "dto/common/publicTransport.json"),
            new ImmutablePair(Transportation.class, "dto/common/cycling.json"),
            new ImmutablePair(Transportation.class, "dto/common/train.json")
        );

        for(ImmutablePair<Class<Object>, String> json : jsons) {
            String expectedContent = Common.readFile(json.getValue());
            String resultContent = JsonUtils.toJsonPretty(JsonUtils.fromJson(expectedContent, json.getKey()));
            Assert.assertEquals(expectedContent, resultContent);
        }
    }
}
