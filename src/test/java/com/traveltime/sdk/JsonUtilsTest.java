package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class JsonUtilsTest {
    @Test
    public void shouldParseAllJsonFiles() throws IOException {
        List<Pair<Class<Object>, String>> jsons = Arrays.asList(
            // requests
            new Pair(TimeFilterRequest.class, "dto/requests/timeFilterRequest.json"),
            new Pair(TimeMapRequest.class, "dto/requests/timeMapRequest.json"),
            // responses
            new Pair(TimeMapResponse.class, "dto/responses/timeMapResponse.json"),
            new Pair(TimeFilterResponse.class, "dto/responses/timeFilterResponse.json"),
            // transportations
            new Pair(Transportation.class, "dto/common/bus.json"),
            new Pair(Transportation.class, "dto/common/driving.json"),
            new Pair(Transportation.class, "dto/common/ferry.json"),
            new Pair(Transportation.class, "dto/common/walking.json"),
            new Pair(Transportation.class, "dto/common/coach.json"),
            new Pair(Transportation.class, "dto/common/publicTransport.json"),
            new Pair(Transportation.class, "dto/common/cycling.json"),
            new Pair(Transportation.class, "dto/common/train.json")
        );

        for(Pair<Class<Object>, String> json : jsons) {
            String content = Common.readFile(json.getValue());
            String resultContent = JsonUtils.toJson(JsonUtils.fromJson(content, json.getKey()));
            Assert.assertEquals(content, resultContent);
        }
    }
}
