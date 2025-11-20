package com.traveltime.sdk.server_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.server_tests.dto.TestServerResponse;
import com.traveltime.sdk.server_tests.dto.details.NoDetails;
import com.traveltime.sdk.server_tests.dto.details.SessionCreated;
import com.traveltime.sdk.server_tests.values.*;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class TestServer {

    private final URI serverUri;
    private final OkHttpClient httpClient;
    private final TravelTimeCredentials credentials;
    private final TravelTimeSDK sdk;

    private final TypeReference<TestServerResponse<NoDetails>> noDetailsTypeRef =
        new TypeReference<TestServerResponse<NoDetails>>() {};

    private final TypeReference<TestServerResponse<SessionCreated>> sessionCreatedDetailsTypeRef =
        new TypeReference<TestServerResponse<SessionCreated>>() {};

    public TestServer() {
        Map<String, String> env = System.getenv();
        serverUri = URI.create(env.getOrDefault("TEST_SERVER_URI", "http://localhost:8080/"));
        httpClient = new OkHttpClient();
        credentials = new TravelTimeCredentials("app_id", "api_key");
        sdk = TravelTimeSDK.builder()
            .client(httpClient)
            .credentials(credentials)
            .baseUri(serverUri.resolve("v4"))
            .baseProtoUri(serverUri.resolve("proto"))
            .build();
    }

    public InSession executeStartSessionReq() {
        SessionId sessionId = execControlRequest(newControlReqBuilder("/start"), sessionCreatedDetailsTypeRef)
            .flatMap(r -> r.toSuccessfulResponse())
            .map(a -> a.getDetails())
            .getOrElseThrow(e -> e)
            .getSessionId();

        return new InSession(sessionId);
    }

    public class InSession {
        @Getter
        private final AddControlHeaders addControlHeaders;

        public InSession(SessionId sessionId) {
            this.addControlHeaders = new AddControlHeaders(sessionId);
        }

        public TravelTimeSDK getSdk() {
            return sdk;
        }

        public void executeStopSessionReq() {
            Request.Builder req = addControlHeaders.toStopSessionRequest(newControlReqBuilder("/stop"));
            execControlRequest(req, noDetailsTypeRef).getOrElseThrow(a -> a);
        }

        public <Details> Either<TestFailedError, TestServerResponse<Details>>
        executeStartTestRequest(
            Endpoint endpoint, TestName testName, TypeReference<TestServerResponse<Details>> typeRef
        ) {
            Request.Builder req =
                addControlHeaders.toStartTestRequest(newControlReqBuilder("/start_test"), endpoint, testName);
            return execControlRequest(req, typeRef);
        }

        public <T> Either<TestFailedError, T> executeApiRequestMapError(TravelTimeRequest<T> request) {
            return TestFailedError.mapTravelTimeError(sdk.send(request));
        }

        public <T> Either<TestFailedError, T> executeApiRequestMapError(ProtoRequest<T> request) {
            return TestFailedError.mapTravelTimeError(sdk.sendProto(request));
        }

        public <T> Either<TravelTimeError, T> executeApiRequest(TravelTimeRequest<T> request) {
            return sdk.send(request);
        }

        public <T> Either<TravelTimeError, T> executeApiRequest(ProtoRequest<T> request) {
            return sdk.sendProto(request);
        }

        public Either<TestFailedError, Nothing> executeValidationRequest(
            Object payload,
            Endpoint endpoint,
            TestName testName
        ) {
            return
                TestFailedError
                    .mapTravelTimeError(JsonUtils.toJson(payload))
                    .flatMap(json -> {
                        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
                        Request.Builder req = addControlHeaders.toValidationRequest(newControlReqBuilder("/validate"), endpoint, testName);
                        return execControlRequest(req.post(body), noDetailsTypeRef);
                    })
                    .map(r -> new Nothing());
        }
    }

    @SneakyThrows
    private Request.Builder newControlReqBuilder(String url) {
        val builder = new Request.Builder().headers(credentials.getHeaders());
        credentials
            .getBasicCredentialsHeaders()
            .forEach(pair -> builder.addHeader(pair.component1(), pair.component2()));
        return builder
            .addHeader("User-Agent", TravelTimeSDK.fullName())
            .url(serverUri.resolve(url).toURL())
            .post(RequestBody.create(new byte[]{}));
    }

    private <T> Stream<T> toStream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    }

    private <Details> Either<TestFailedError, TestServerResponse<Details>> execControlRequest(
        Request.Builder req, TypeReference<TestServerResponse<Details>> typeRef
    ) {

        return TestFailedError
            .attempt(
                () -> httpClient.newCall(req.build()).execute(),
                "Error while executing control request")
            .flatMap(r -> bodyAsString(r))
            .flatMap(s -> {
                System.out.println(s);
                return TestFailedError
                    .mapTravelTimeError(JsonUtils.fromJson(s, typeRef))
                    .flatMap(r -> r.toSuccessfulResponse());
            });
    }

    private Either<TestFailedError, String> bodyAsString(Response resp) {
        return TestFailedError.attempt(
            () -> resp.body().string(),
            "Error when trying to extract String body from response");
    }
}
