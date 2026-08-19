package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.GeohashFastProtoRequest;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.GeohashFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.RequestError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.junit.Assert;
import org.junit.Test;

public class ProtoConnectionReuseTest {
    private static final int REQUESTS = 3;

    /**
     * A 404 carries a body the proto path never reads, so its connection is only released once the
     * response is closed. HTTP/1.1 is forced to make that observable as a connection count; over
     * HTTP/2 the same leak holds a stream open instead, which the pool does not expose.
     */
    @Test
    public void shouldReuseConnectionAcrossNotFoundProtoRequests() {
        ConnectionPool pool = new ConnectionPool(REQUESTS + 2, 5, TimeUnit.MINUTES);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionPool(pool)
                .protocols(Arrays.asList(Protocol.HTTP_1_1))
                .build();

        TravelTimeSDK sdk = TravelTimeSDK.builder()
                .credentials(new TravelTimeCredentials(System.getenv("APP_ID"), System.getenv("API_KEY")))
                .client(client)
                .build();

        for (int i = 0; i < REQUESTS; i++) {
            Either<TravelTimeError, GeohashFastProtoResponse> response = sdk.sendProto(notFoundRequest());
            Assert.assertTrue("unknown country must not resolve to a route", response.isLeft());
            Assert.assertTrue(
                    "test needs a 404 with a body; a different error would not exercise the leak",
                    response.getLeft() instanceof RequestError);
        }

        Assert.assertEquals(
                "every 404 must release its connection back to the pool",
                pool.connectionCount(),
                pool.idleConnectionCount());
    }

    private GeohashFastProtoRequest notFoundRequest() {
        return GeohashFastProtoRequest.builder()
                .originCoordinate(new Coordinates(51.507609, -0.128315))
                .transportation(Transportation.Modes.DRIVING_FERRY)
                .travelTime(900)
                .resolution(6)
                .country(new Country.Custom("zz"))
                .requestType(RequestType.ONE_TO_MANY)
                .build();
    }
}
