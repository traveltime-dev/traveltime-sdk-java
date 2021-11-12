package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterDistrictsRequest;
import com.traveltime.sdk.dto.requests.TimeFilterPostcodesRequest;
import com.traveltime.sdk.dto.requests.TimeFilterSectorsRequest;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.TimeFilterDistrictsResponse;
import com.traveltime.sdk.dto.responses.TimeFilterPostcodesResponse;
import com.traveltime.sdk.dto.responses.TimeFilterSectorsResponse;
import com.traveltime.sdk.dto.responses.TravelTimeResponse;
import com.traveltime.sdk.exceptions.RequestValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class TimeFilterZonesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("APP_ID"), System.getenv("API_KEY"));
    }

    @Test
    public void shouldSendTimeFilterDistrictsRequest() throws IOException, RequestValidationException {
        Coordinates coordinates = new Coordinates(51.508930,-0.131387);
        Transportation transport = new PublicTransport();

        TimeFilterDistrictsRequest request = new TimeFilterDistrictsRequest(
            createDepartureSearch(coordinates, transport),
            createArrivalSearch(coordinates, transport)
        );

        TravelTimeResponse<TimeFilterDistrictsResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    @Test
    public void shouldSendTimeFilterSectorsRequest() throws IOException, RequestValidationException {
        Coordinates coordinates = new Coordinates(51.508930,-0.131387);
        Transportation transport = new PublicTransport();

        TimeFilterSectorsRequest request = new TimeFilterSectorsRequest(
            createDepartureSearch(coordinates, transport),
            createArrivalSearch(coordinates, transport)
        );

        TravelTimeResponse<TimeFilterSectorsResponse> response = sdk.send(request);

        Assert.assertEquals(200, (int)response.getHttpCode());
        Assert.assertNotNull(response.getParsedBody());
    }

    private Iterable<DepartureSearch> createDepartureSearch(Coordinates coordinates, Transportation transportation) {
        DepartureSearch ds = new DepartureSearch(
            "Test departure search",
            coordinates,
            transportation,
            Date.from(Instant.now()),
            900,
            0.1,
            Arrays.asList(Property.COVERAGE)
        );
        return Collections.singletonList(ds);
    }

    private Iterable<ArrivalSearch> createArrivalSearch(Coordinates coordinates, Transportation transportation) {
        ArrivalSearch as = new ArrivalSearch(
            "Test arrival search",
            coordinates,
            transportation,
            Date.from(Instant.now()),
            900,
            0.1,
            Arrays.asList(Property.COVERAGE),
            new FullRange(true, 1, 300)
        );
        return Collections.singletonList(as);
    }
}
