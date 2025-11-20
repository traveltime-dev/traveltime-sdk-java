package com.traveltime.sdk.server_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.dto.requests.ProtoRequest;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.server_tests.dto.TestServerResponse;
import com.traveltime.sdk.server_tests.values.*;
import io.vavr.control.Either;

import java.util.function.Function;

public class ApiEndpointTestRunner {

    private final TestServer.InSession server;
    private final Endpoint endpoint;

    public ApiEndpointTestRunner(TestServer.InSession server, Endpoint endpoint) {
        this.server = server;
        this.endpoint = endpoint;
    }

    /**
     * Run test with `testName` that expects success API response of type `Resp`.
     *
     * @param <Params>   Type of test parameters
     * @param <Req>      Type of API request
     * @param <Resp>     Type of API response
     * @param <ChalResp> Type of challenge response
     */
    <Params, Req extends TravelTimeRequest<Resp>, Resp, ChalResp> void runJson(
        TestName testName,
        TypeReference<TestServerResponse<Params>> paramsTypeRef,
        Function<Params, Req> makeRequest,
        Function<Resp, ChalResp> solveChallenge
    ) {
        server
            .executeStartTestRequest(endpoint, testName, paramsTypeRef)
            .flatMap(r -> server.executeApiRequestMapError(makeRequest.apply(r.getDetails())))
            .flatMap(r -> server.executeValidationRequest(solveChallenge.apply(r), endpoint, testName))
            .orElseRun(e -> {
                throw e;
            });
    }

    /**
     * Run test with `testName` that expects success API response of type `Resp`.
     *
     * @param <Params>   Type of test parameters
     * @param <Req>      Type of API request
     * @param <Resp>     Type of API response
     * @param <ChalResp> Type of challenge response
     */
    <Params, Req extends ProtoRequest<Resp>, Resp, ChalResp> void runProto(
        TestName testName,
        TypeReference<TestServerResponse<Params>> paramsTypeRef,
        Function<Params, Req> makeRequest,
        Function<Resp, ChalResp> solveChallenge
    ) {
        server
            .executeStartTestRequest(endpoint, testName, paramsTypeRef)
            .flatMap(r -> server.executeApiRequestMapError(makeRequest.apply(r.getDetails())))
            .flatMap(r -> server.executeValidationRequest(solveChallenge.apply(r), endpoint, testName))
            .orElseRun(e -> {
                throw e;
            });
    }


    /**
     * Run test with `testName` that expects API error.
     *
     * @param <Params>   Type of test parameters
     * @param <Req>      Type of API request
     * @param <Resp>     Type of API response
     * @param <ChalResp> Type of challenge response
     */
    <Params, Req extends TravelTimeRequest<Resp>, Resp, ChalResp> void runApiError(
        TestName testName,
        TypeReference<TestServerResponse<Params>> paramsTypeRef,
        Function<Params, Req> makeRequest,
        Function<ResponseError, ChalResp> solveChallenge
    ) {
        server
            .executeStartTestRequest(endpoint, testName, paramsTypeRef)
            .flatMap(r ->
                server
                    .executeApiRequest(makeRequest.apply(r.getDetails()))
                    .fold(this::expectResponseError, this::responseToError)
            )
            .flatMap(e -> server.executeValidationRequest(solveChallenge.apply(e), endpoint, testName))
            .orElseRun(e -> {
                throw e;
            });
    }

    private Either<TestFailedError, ResponseError> expectResponseError(TravelTimeError e) {
        if (e instanceof ResponseError) {
            return Either.right((ResponseError) e);
        } else {
            String msg = "Expected ResponseError, but got " + e.getClass().getSimpleName() + ". Message: " + e.getMessage();
            return Either.left(TestFailedError.toTestFailedError(e, msg));
        }
    }

    private <Resp, R> Either<TestFailedError, R> responseToError(Resp resp) {
        String message = "Expected error response from API, but got a proper one: " + resp;
        return Either.left(new TestFailedError(message));
    }
}
