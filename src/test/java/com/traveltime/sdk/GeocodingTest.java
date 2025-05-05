package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.GeocodingRequest;
import com.traveltime.sdk.dto.requests.ReverseGeocodingRequest;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.geocoding.Properties;
import com.traveltime.sdk.dto.responses.mapinfo.Feature;
import com.traveltime.sdk.dto.responses.mapinfo.PublicTransport;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;

public class GeocodingTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials = new TravelTimeCredentials(
            System.getenv("APP_ID"),
            System.getenv("API_KEY")
        );
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendGeocodingRequest() {
        GeocodingRequest request = GeocodingRequest
                .builder()
                .query("Geneva")
                .withinCountries(Arrays.asList("CH", "DE"))
                .limit(1)
                .build();
        Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendReverseGeocodingRequest() {
        ReverseGeocodingRequest request = new ReverseGeocodingRequest(new Coordinates(51.507281, -0.132120));
        Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldParseGeocodingResponse() throws IOException {

        String expectedContent = Common.readFile("dto/responses/geocodingResponse.json");
        Properties expectedProperties = new Properties(
            "Bern, Bern-Mittelland administrative district, Bernese Mittelland administrative region, Bern, Switzerland",
            "Bern, Bern-Mittelland administrative district, Bernese Mittelland administrative region, Bern, Switzerland",
            0.78,
            "boundary",
            "administrative",
            null,
            null,
            "Bernese Mittelland administrative region",
            null,
            null,
            "Bern-Mittelland administrative district",
            "Bern",
            "Bern",
            null,
            null,
            "Switzerland",
            "CHE",
            null,
            null,
            null,
            new Feature(
                Collections.emptyList(),
                new PublicTransport(
                        OffsetDateTime.parse("2021-11-17T16:00:00Z"),
                        OffsetDateTime.parse("2022-01-20T16:00:00Z")
                ),
                null,
                false,
                false
            )
        );

        Either<TravelTimeError, GeocodingResponse> fromJson = JsonUtils.fromJson(expectedContent, GeocodingResponse.class);
        GeocodingResponse geocodingResponse = fromJson.get();
        String result = JsonUtils.toJsonPretty(geocodingResponse).get();
        Assert.assertEquals(expectedContent, result);
        Assert.assertEquals(geocodingResponse.getFeatures().get(0).getProperties(), expectedProperties);
        Assert.assertTrue(geocodingResponse.toString().contains("Bern, Bern-Mittelland administrative district, Bernese Mittelland administrative region, Bern, Switzerland"));
    }
}
