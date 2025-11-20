package com.traveltime.sdk.server_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.requests.SupportedLocationsRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.server_tests.dto.TestServerResponse;
import com.traveltime.sdk.server_tests.dto.details.NoDetails;
import com.traveltime.sdk.server_tests.values.*;
import io.vavr.control.Either;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SupportedLocationsTest extends CommonErrorTests {

    @Override
    protected Endpoint endpoint() {
        return new Endpoint("/v4/supported-locations");
    }

    @Override
    protected TravelTimeRequest<?> makeRequestForErrorTest(TestName testName) {
        return new SupportedLocationsTestRequest(testName, LocationsGen.singletonList());
    }

    private final class SupportedLocationsTestRequest extends SupportedLocationsRequest {
        TestName testName;

        public SupportedLocationsTestRequest(TestName testName, @NonNull List<Location> locations) {
            super(locations);
            this.testName = testName;
        }

        @Override
        public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
            return super.createRequest(baseUri, credentials).map(
                req -> server.getAddControlHeaders().toApiRequest(req, testName)
            );
        }
    }

    private Function<NoDetails, SupportedLocationsRequest> makeReqSingleLocation(TestName testName) {
        return p -> new SupportedLocationsTestRequest(testName, LocationsGen.singletonList());
    }

    @Test
    public void singleLocation() {
        val testName = new TestName("single_location");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            makeReqSingleLocation(testName),
            r -> {
                String mapName = r.getLocations().getFirst().getMapName();
                return Collections.singletonMap("map_name", mapName);
            }
        );
    }

    @Value
    @Builder
    @Jacksonized
    private static class NumLocationsParam {
        int numLocations;
    }

    private Function<NumLocationsParam, SupportedLocationsRequest> makeReqFromNumLocations(TestName testName) {
        return p -> new SupportedLocationsTestRequest(testName, LocationsGen.listOfN(p.numLocations));
    }

    private final TypeReference<TestServerResponse<NumLocationsParam>> numLocationsParamTypeRef =
        new TypeReference<TestServerResponse<NumLocationsParam>>() {};

    @Test
    public void multipleLocations() {
        val testName = new TestName("multiple_locations");

        runner.runJson(
            testName,
            numLocationsParamTypeRef,
            makeReqFromNumLocations(testName),
            r -> r.getLocations().stream().collect(
                Collectors.groupingBy(
                    loc -> loc.getMapName(),
                    Collectors.mapping(loc -> loc.getId(), Collectors.toList())
                )
            )
        );
    }

    @Test
    public void unsupportedLocations() {
        val testName = new TestName("unsupported_locations");

        runner.runJson(
            testName,
            numLocationsParamTypeRef,
            makeReqFromNumLocations(testName),
            r -> {
                val unsupported = r.getUnsupportedLocations();
                return new HashMap<String, Object>() {{
                    put("ids", unsupported);
                    put("size", unsupported.size());
                }};
            }
        );
    }

    @Test
    public void additionalMapsSingle() {
        val testName = new TestName("additional_maps_single");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            makeReqSingleLocation(testName),
            r -> {
                val loc = r.getLocations().getFirst();
                val map1 = loc.getMapName();
                val map2 = loc.getMapName();
//                val map2 = loc.getAdditionalMapNames().getFirst();
                return Arrays.asList(map1, map2);
            }
        );
    }

    @Test
    public void additionalMapsMultiple() {
        val testName = new TestName("additional_maps_multiple");

        runner.runJson(
            testName,
            numLocationsParamTypeRef,
            makeReqFromNumLocations(testName),
            r -> collectSupported(r)
        );
    }

    @Test
    public void mixedAdditionalAndUnsupported() {
        val testName = new TestName("mixed_additional_and_unsupported");

        runner.runJson(
            testName,
            numLocationsParamTypeRef,
            makeReqFromNumLocations(testName),
            r -> new HashMap<String, Object>() {{
                put("supported", collectSupported(r));
                put("unsupported", r.getUnsupportedLocations());
            }}
        );
    }

    private Map<String, List<String>> collectSupported(SupportedLocationsResponse resp) {
        return resp.getLocations().stream().collect(
            Collectors.toMap(
                loc -> loc.getId(),
                loc -> Stream.concat(
                    Stream.of(loc.getMapName()),
                    loc.getAdditionalMapNames().stream()
                ).collect(Collectors.toList())
            )
        );
    }
}
