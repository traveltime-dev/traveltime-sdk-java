package com.traveltime.sdk;

import com.traveltime.sdk.auth.KeyAuth;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.SupportedLocationsRequest;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SupportedLocationsTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials = new KeyAuth(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendSupportLocationsRequest() {
        List<Location> locations = Arrays.asList(
            new Location("location1", new Coordinates(51.508930,-0.131387)),
            new Location("location2", new Coordinates(51.508824,-0.167093)),
            new Location("location3", new Coordinates(51.536067,-0.153596))
        );
        SupportedLocationsRequest request = new SupportedLocationsRequest(locations);

        Either<TravelTimeError, SupportedLocationsResponse> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }
}
