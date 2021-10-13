package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class JsonTest {

    @Test
    public void shouldParseTimeFilterRequest() throws IOException {
        String content = Common.readFile("dto/requests/timeFilterRequest.json");
        String resultContent = Json.toJson(Json.fromJson(content, TimeFilterRequest.class));
        Assert.assertEquals(content, resultContent);
    }

    @Test
    public void shouldParseTimeMapRequest() throws IOException {
        String content = Common.readFile("dto/requests/timeMapRequest.json");
        String resultContent = Json.toJson(Json.fromJson(content, TimeMapRequest.class));
        Assert.assertEquals(content, resultContent);
    }

    @Test
    public void shouldParseTimeMapResponse() throws IOException {
        String content = Common.readFile("dto/responses/timeMapResponse.json");
        String resultContent = Json.toJson(Json.fromJson(content, TimeMapResponse.class));
        Assert.assertEquals(content, resultContent);
    }

    @Test
    public void shouldParseTimeFilterResponse() throws IOException {
        String content = Common.readFile("dto/responses/timeFilterResponse.json");
        String resultContent = Json.toJson(Json.fromJson(content, TimeFilterResponse.class));
        Assert.assertEquals(content, resultContent);
    }
}
