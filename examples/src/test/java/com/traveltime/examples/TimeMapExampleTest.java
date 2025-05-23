package com.traveltime.examples;

import static com.traveltime.examples.TimeMapExample.findCafesWithinDrivingDistance;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TimeMapExampleTest {
    @Test
    public void shouldReturnValidResponse() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        Coordinates origin = new Coordinates(51.41070, -0.15540);

        List<Map.Entry<String, Coordinates>> cafes = Utils.generateLocations("cafe", origin, 0.5, 100);

        List<String> reachableCafes = findCafesWithinDrivingDistance(sdk, origin, cafes, 1800);

        Assert.assertFalse(reachableCafes.isEmpty());
    }
}
