package com.traveltime.sdk;

import com.traveltime.sdk.dto.requests.MapInfoRequest;
import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MapInfoTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendGeocodingRequest() {
        MapInfoRequest request = new MapInfoRequest();
        Either<TravelTimeError, MapInfoResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }
}
