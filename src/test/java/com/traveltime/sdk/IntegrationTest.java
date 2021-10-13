package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IntegrationTest {

    @Test
    public void ShouldSendTimeFilterRequest() throws IOException {
        String requestJson = Common.readFile("dto/requests/timeFilterRequest.json");

        TimeFilterRequest timeFilterRequest = Json.fromJson(requestJson, TimeFilterRequest.class);


        OkHttpClient client = new OkHttpClient();
        TravelTimeSDK sdk = new TravelTimeSDK(client, "ed0b2f22", "4d5366b527c0e3868ee1546e70f6eb90");

        TimeFilterResponse timeFilterResponse = sdk.send(timeFilterRequest);

        Assert.assertNotNull(timeFilterResponse);
    }

    @Test
    public void ShouldSendTimeMapRequest() throws IOException {
        String requestJson = Common.readFile("dto/requests/timeMapRequest.json");

        TimeMapRequest timeMapRequest = Json.fromJson(requestJson, TimeMapRequest.class);


        OkHttpClient client = new OkHttpClient();
        TravelTimeSDK sdk = new TravelTimeSDK(client, "ed0b2f22", "4d5366b527c0e3868ee1546e70f6eb90");

        TimeMapResponse timeMapResponse = sdk.send(timeMapRequest);

        Assert.assertNotNull(timeMapResponse);
    }
}
