package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.server_tests.values.*;
import okhttp3.Request;

public final class AddControlHeaders {
    private static final String NAME_SESSION_ID = "Session-Id";
    private static final String NAME_ENDPOINT = "Endpoint";
    private static final String NAME_TEST_NAME = "Test-Name";

    private final SessionId sessionId;

    public AddControlHeaders(SessionId sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Adds Session-Id and Test-Name headers to given request
     */
    public Request toApiRequest(Request request, TestName testName) {
        return new Request.Builder(request)
            .addHeader(NAME_SESSION_ID, sessionId.getValue())
            .addHeader(NAME_TEST_NAME, testName.getValue())
            .build();
    }

    /**
     * Adds Session-Id, Endpoint and Test-Name headers to given request
     */
    public Request.Builder toStartTestRequest(Request.Builder request, Endpoint endpoint, TestName testName) {
        return toValidationRequest(request, endpoint, testName);
    }

    /**
     * Adds Session-Id, Endpoint and Test-Name headers to given request
     */
    public Request.Builder toValidationRequest(Request.Builder request, Endpoint endpoint, TestName testName) {
        return request
            .addHeader(NAME_SESSION_ID, sessionId.getValue())
            .addHeader(NAME_TEST_NAME, testName.getValue())
            .addHeader(NAME_ENDPOINT, endpoint.getValue());
    }

    /**
     * Adds Session-Id header to given request
     */
    public Request.Builder toStopSessionRequest(Request.Builder request) {
        return request.addHeader(NAME_SESSION_ID, sessionId.getValue());
    }
}
