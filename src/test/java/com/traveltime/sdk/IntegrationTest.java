package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.transportation.Coach;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.requests.timemap.DepartureSearch;
import com.traveltime.sdk.dto.responses.HttpResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class IntegrationTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK("ed0b2f22", "4d5366b527c0e3868ee1546e70f6eb90");
    }

    @Test
    public void shouldSendTimeFilterRequest() throws IOException {
        String requestJson = Common.readFile("dto/requests/timeFilterRequest.json");
        TimeFilterRequest timeFilterRequest = Json.fromJson(requestJson, TimeFilterRequest.class);
        HttpResponse<TimeFilterResponse> timeFilterResponse = sdk.send(timeFilterRequest);
        System.out.println(timeFilterResponse.getErrorMessage());
        Assert.assertEquals(200, timeFilterResponse.getHttpCode());
        Assert.assertNotNull(timeFilterResponse.getParsedBody());
    }

    @Test
    public void shouldSendTimeMapRequest() throws IOException {
        String requestJson = Common.readFile("dto/requests/timeMapRequest.json");
        TimeMapRequest timeMapRequest = Json.fromJson(requestJson, TimeMapRequest.class);

        HttpResponse<TimeMapResponse> timeMapResponse = sdk.send(timeMapRequest);
        Assert.assertEquals(200, timeMapResponse.getHttpCode());
        Assert.assertNotNull(timeMapResponse.getParsedBody());
    }
/*
    @Test
    public void test() {
        DepartureSearch c = DepartureSearch.builder().id("re").build();
    }*/
}
