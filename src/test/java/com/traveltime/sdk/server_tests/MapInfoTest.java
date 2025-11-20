package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.MapInfoRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.mapinfo.*;
import com.traveltime.sdk.server_tests.values.Endpoint;
import com.traveltime.sdk.server_tests.values.TestName;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.val;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapInfoTest extends CommonErrorTests {

    @Override
    protected Endpoint endpoint() {
        return new Endpoint("/v4/map-info");
    }

    private final class MapInfoTestRequest extends MapInfoRequest {
        TestName testName;

        public MapInfoTestRequest(TestName testName) {
            super();
            this.testName = testName;
        }

        @Override
        public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
            return super.createRequest(baseUri, credentials).map(
                req -> server.getAddControlHeaders().toApiRequest(req, testName)
            );
        }
    }

    @Override
    protected TravelTimeRequest<?> makeRequestForErrorTest(TestName testName) {
        return new MapInfoTestRequest(testName);
    }

    @Test
    public void singleMapMinimal() {
        val testName = new TestName("single_map_minimal");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            p -> new MapInfoTestRequest(testName),
            r -> r.getMaps().stream().map(m -> m.getName()).collect(Collectors.toList())
        );
    }

    @Test
    public void faresPostcodeSupport() {
        val testName = new TestName("fares_postcode_support");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            p -> new MapInfoTestRequest(testName),
            r -> {
                Predicate<Feature> predPostcodes = Feature::getPostcodes;
                Predicate<Feature> predFares = Feature::getFares;
                return new HashMap<String, List<String>>() {{
                    put("have_postcodes", collectMapNames(r, predPostcodes));
                    put("have_fares", collectMapNames(r, predFares));
                    put("no_postcodes", collectMapNames(r, predPostcodes.negate()));
                    put("no_fares", collectMapNames(r, predFares.negate()));
                }};
            }
        );
    }

    private List<String> collectMapNames(MapInfoResponse response, Predicate<Feature> pred) {
        return response.getMaps().stream()
            .filter(m -> pred.test(m.getFeatures()))
            .map(m -> m.getName())
            .collect(Collectors.toList());
    }

    @Test
    public void crossCountryModes() {
        val testName = new TestName("cross_country_modes");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            p -> new MapInfoTestRequest(testName),
            r -> {
                val crossCountryMaps = r.getMaps().stream()
                    .flatMap(map -> modeToMapName(map))
                    .collect(
                        Collectors.groupingBy(
                            t -> t._1(),
                            Collectors.mapping(t -> t._2(), Collectors.toList())
                        )
                    );

                val noCrossCountry = r.getMaps().stream()
                    .filter(map -> crossCountryModesOf(map).isEmpty())
                    .map(map -> map.getName())
                    .collect(Collectors.toList());

                return new HashMap<String, List<String>>() {{
                    putAll(crossCountryMaps);
                    put("no_cross_country", noCrossCountry);
                }};
            }
        );
    }

    @Test
    public void publicTransportDate() {
        val testName = new TestName("public_transport_dates");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            p -> new MapInfoTestRequest(testName),
            r -> {
                val ptDatas =
                    r.getMaps().stream().flatMap(map ->
                        Option.of(map.getFeatures().getPublicTransport()).toJavaStream()
                    ).collect(Collectors.toList());

                val cmp = OffsetDateTime.timeLineOrder();
                val dateStart = ptDatas.stream().map(a -> a.getDateStart()).min(cmp).get();
                val dateEnd = ptDatas.stream().map(a -> a.getDateEnd()).max(cmp).get();
                val mapNamesWithPt = collectMapNames(r, a -> a.getPublicTransport() != null);
                val mapNamesNoPt = collectMapNames(r, a -> a.getPublicTransport() == null);

                return new HashMap<String, Object>() {{
                    put("date_start", dateStart);
                    put("date_end", dateEnd);
                    put("with_public_transport", mapNamesWithPt);
                    put("no_public_transport", mapNamesNoPt);
                }};
            }
        );
    }

    @Test
    public void timeFilterFast() {
        val testName = new TestName("time_filter_fast");

        runner.runJson(
            testName,
            TypeRefs.noTestParams,
            p -> new MapInfoTestRequest(testName),
            r -> {
                val mapAndSupported = r.getMaps().stream().flatMap(map ->
                    Option
                        .of(map.getFeatures().getTimeFilterFast())
                        .toJavaStream()
                        .flatMap(tft -> tft.getPeriods().stream())
                        .flatMap(p -> p.getSupported().stream())
                        .map(s -> new Tuple2<>(map.getName(), s))
                );

                val fastMaps = mapAndSupported
                    .collect(Collectors.groupingBy(t -> t._2, Collectors.mapping(t -> t._1, Collectors.toList())))
                    .entrySet().stream()
                    .map(e -> new HashMap<String, Object>() {{
                        put("search_type", e.getKey().getSearchType());
                        put("transportation_type", e.getKey().getTransportationMode());
                        put("maps", e.getValue());
                    }})
                    .collect(Collectors.toList());

                val simpleMaps = collectMapNames(r, a -> a.getTimeFilterFast() == null);

                return new HashMap<String, Object>() {{
                    put("fast_maps", fastMaps);
                    put("simple_maps", simpleMaps);
                }};
            }
        );
    }

    private List<String> crossCountryModesOf(Map map) {
        return map.getFeatures().getCrossCountryModes();
    }

    private Stream<Tuple2<String, String>> modeToMapName(Map map) {
        return crossCountryModesOf(map).stream().map(mode -> new Tuple2<>(mode, map.getName()));
    }
}
