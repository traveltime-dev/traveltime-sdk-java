package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TimeFilterFastProtoExampleTest {
    @Test
    public void shouldReturnValidResponse() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        Either<TravelTimeError, List<Map.Entry<Coordinates, Integer>>> result =
                TimeFilterFastProtoExample.getResults(sdk);
        Assert.assertTrue(result.isRight());
    }
}
