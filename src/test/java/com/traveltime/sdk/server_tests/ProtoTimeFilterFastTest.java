package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.*;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.server_tests.values.Endpoint;
import com.traveltime.sdk.server_tests.values.TestName;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.val;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.Test;

import java.util.List;

public class ProtoTimeFilterFastTest extends BaseEndpointTest {
    @Override
    protected Endpoint endpoint() {
        return new Endpoint("/proto/<country>/time-filter/fast");
    }

    private final class TimeFilterFastProtoTestRequest extends TimeFilterFastProtoRequest {
        TestName testName;

        public TimeFilterFastProtoTestRequest(
            TestName testName,
            @NonNull Coordinates originCoordinate,
            @NonNull List<Coordinates> destinationCoordinates,
            @NonNull Transportation transportation,
            @NonNull Integer travelTime,
            @NonNull Country country,
            @NonNull RequestType requestType,
            boolean withDistance
        ) {
            super(originCoordinate, destinationCoordinates, transportation, travelTime, country, requestType, withDistance);
            this.testName = testName;
        }

        @Override
        public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
            return super.createRequest(baseUri, credentials).map(
                req -> server.getAddControlHeaders().toApiRequest(req, testName)
            );
        }
    }

    @Test
    public void simplestRequest() {
        val testName = new TestName("simplest_request");

        runner.runProto(
            testName,
            TypeRefs.noTestParams,
            p -> new TimeFilterFastProtoTestRequest(
                testName,
                LocationsGen.coordinates(),
                LocationsGen.coordinatesSingletonList(),
                Transportation.Modes.DRIVING_FERRY,
                3600,
                new Country.Custom("world"),
                RequestType.MANY_TO_ONE,
                false
            ),
            (TimeFilterFastProtoResponse r) -> r.getTravelTimes().getFirst()
        );
    }
}
