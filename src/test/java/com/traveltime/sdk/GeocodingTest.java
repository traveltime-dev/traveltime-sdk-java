package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.GeocodingRequest;
import com.traveltime.sdk.dto.requests.ReverseGeocodingRequest;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.geojson.FeatureCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class GeocodingTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendGeocodingRequest() {
        GeocodingRequest request = new GeocodingRequest("Geneva", Arrays.asList("CH", "DE"), 1);
        Either<TravelTimeError, FeatureCollection> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }

    @Test
    public void shouldSendReverseGeocodingRequest() {
        ReverseGeocodingRequest request = new ReverseGeocodingRequest(
            new Coordinates(51.507281, -0.132120),
            Collections.singletonList("GB")
        );
        Either<TravelTimeError, FeatureCollection> response = sdk.send(request);
        Assert.assertTrue(response.isRight());
    }
}
