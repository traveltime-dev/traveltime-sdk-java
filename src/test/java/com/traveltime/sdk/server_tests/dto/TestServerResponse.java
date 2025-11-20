package com.traveltime.sdk.server_tests.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.traveltime.sdk.server_tests.TestFailedError;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.NonNull;
import lombok.Value;


@Value
@JsonDeserialize(using = TestServerResponseDeserializerBootstrap.class)
public class TestServerResponse<Details> {

    public enum Status {
        SUCCESS, ERROR;

        @JsonCreator
        public static Status from(String raw) {
            return Status.valueOf(raw.toUpperCase());
        }
    }

    @NonNull
    Status status;

    int code;

    @NonNull
    String type;

    @NonNull
    String message;

    @NonNull
    Details details;

    public Either<TestFailedError, TestServerResponse<Details>> toSuccessfulResponse() {
        switch (status) {
            case SUCCESS:
                return Either.right(this);
            case ERROR:
                return Either.left(new TestFailedError(message));
            default:
                throw new IllegalStateException("Unexpected status: " + status);
        }
    }

    public Option<TestFailedError> toNoneIfSuccessful() {
        return toSuccessfulResponse().fold(
            e -> Option.some(e),
            r -> Option.none()
        );
    }
}
