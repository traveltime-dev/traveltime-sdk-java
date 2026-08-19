package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.transportation.PublicTransport;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterDistrictsRequest;
import com.traveltime.sdk.dto.requests.TimeFilterSectorsRequest;
import com.traveltime.sdk.dto.requests.zones.*;
import com.traveltime.sdk.dto.responses.*;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TimeFilterZonesTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY"));
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldSendTimeFilterDistrictsRequest() {
        Coordinates coordinates = new Coordinates(51.508930, -0.131387);
        Transportation transport = PublicTransport.builder().build();

        TimeFilterDistrictsRequest request = new TimeFilterDistrictsRequest(
                createDepartureSearch(coordinates, transport), createArrivalSearch(coordinates, transport));

        Either<TravelTimeError, TimeFilterDistrictsResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    @Test
    public void shouldSendTimeFilterSectorsRequest() {
        Coordinates coordinates = new Coordinates(51.508930, -0.131387);
        Transportation transport = PublicTransport.builder().build();

        TimeFilterSectorsRequest request = new TimeFilterSectorsRequest(
                createDepartureSearch(coordinates, transport), createArrivalSearch(coordinates, transport));

        Either<TravelTimeError, TimeFilterSectorsResponse> response = sdk.send(request);
        Common.assertResponseIsRight(response);
    }

    private List<DepartureSearch> createDepartureSearch(Coordinates coordinates, Transportation transportation) {
        DepartureSearch ds = DepartureSearch.builder()
                .id("Test departure search")
                .coords(coordinates)
                .transportation(transportation)
                .departureTime(Instant.now())
                .travelTime(900)
                .reachablePostcodesThreshold(0.1)
                .property(Property.COVERAGE)
                .range(new FullRange(true, 1, 300))
                .build();
        return Collections.singletonList(ds);
    }

    private List<ArrivalSearch> createArrivalSearch(Coordinates coordinates, Transportation transportation) {
        ArrivalSearch as = ArrivalSearch.builder()
                .id("Test arrival search")
                .coords(coordinates)
                .transportation(transportation)
                .arrivalTime(Instant.now())
                .travelTime(900)
                .reachablePostcodesThreshold(0.1)
                .property(Property.COVERAGE)
                .range(new FullRange(true, 1, 300))
                .build();
        return Collections.singletonList(as);
    }
}
