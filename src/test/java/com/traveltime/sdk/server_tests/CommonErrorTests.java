package com.traveltime.sdk.server_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.dto.requests.TravelTimeRequest;
import com.traveltime.sdk.server_tests.dto.TestServerResponse;
import com.traveltime.sdk.server_tests.dto.details.NoDetails;
import com.traveltime.sdk.server_tests.values.TestName;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.junit.Test;

public abstract class CommonErrorTests extends BaseEndpointTest {

    protected abstract TravelTimeRequest<?> makeRequestForErrorTest(TestName testName);

    @Value
    @Builder
    @Jacksonized
    private static class ErrorTestAnswer {
        int errorCode;
        int httpStatus;
    }

    private void runErrorTest(String testName) {
        TestName name = new TestName(testName);
        runner.runApiError(
            name,
            new TypeReference<TestServerResponse<NoDetails>>() {},
            p -> makeRequestForErrorTest(name),
            e -> new ErrorTestAnswer(e.getErrorCode(), e.getHttpStatus())
        );
    }

    @Test
    public void noApiKey() {
        runErrorTest("no_api_key");
    }

    @Test
    public void noAppId() {
        runErrorTest("no_app_id");
    }

    @Test
    public void badFormatApiKey() {
        runErrorTest("bad_format_api_key");
    }

    @Test
    public void badFormatAppId() {
        runErrorTest("bad_format_app_id");
    }

    @Test
    public void invalidAppIdOrApiKey() {
        runErrorTest("invalid_app_id_or_api_key");
    }

    @Test
    public void internalError() {
        runErrorTest("internal_error");
    }

    @Test
    public void timedOut() {
        runErrorTest("timed_out");
    }

    @Test
    public void invalidContentType() {
        runErrorTest("invalid_content_type");
    }

    @Test
    public void invalidJson() {
        runErrorTest("invalid_json");
    }

    @Test
    public void jsonSyntaxError() {
        runErrorTest("json_syntax_error");
    }
}
