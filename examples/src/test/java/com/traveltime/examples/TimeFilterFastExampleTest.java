package com.traveltime.examples;

import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.TimeFilterFastResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Test;

public class TimeFilterFastExampleTest {
    @Test
    public void shouldReturnValidResponse() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        TravelTimeSDK sdk = new TravelTimeSDK(credentials);

        Either<TravelTimeError, TimeFilterFastResponse> result = TimeFilterFastExample.executeTimeFilterSearch(sdk);
        Assert.assertTrue(result.isRight());
    }
}
