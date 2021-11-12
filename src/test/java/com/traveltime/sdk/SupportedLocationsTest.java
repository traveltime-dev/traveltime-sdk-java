package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.SupportedLocationsRequest;
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse;
import com.traveltime.sdk.dto.responses.TravelTimeResponse;
import com.traveltime.sdk.exceptions.RequestValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

public class SupportedLocationsTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendSupportLocationsRequest() throws IOException, RequestValidationException {
        Iterable<Location> locations = Arrays.asList(
            new Location("location1", new Coordinates(51.508930,-0.131387)),
            new Location("location2", new Coordinates(51.508824,-0.167093)),
            new Location("location3", new Coordinates(51.536067,-0.153596))
        );
        SupportedLocationsRequest request = new SupportedLocationsRequest(locations);

        TravelTimeResponse<SupportedLocationsResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }
}
