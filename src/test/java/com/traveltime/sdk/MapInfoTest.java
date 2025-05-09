package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.MapInfoRequest;
import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Before;
import org.junit.Test;

public class MapInfoTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendGeocodingRequest() {
        MapInfoRequest request = new MapInfoRequest();
        Either<TravelTimeError, MapInfoResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }
}
