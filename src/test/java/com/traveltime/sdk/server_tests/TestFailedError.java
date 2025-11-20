package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.CheckedFunction0;
import io.vavr.control.Either;
import io.vavr.control.Try;

public class TestFailedError extends RuntimeException {

    public static TestFailedError toTestFailedError(TravelTimeError error, String customMessage) {
        return error.retrieveCause().fold(
            () -> new TestFailedError(customMessage),
            cause -> new TestFailedError(customMessage, cause)
        );
    }

    public static TestFailedError toTestFailedError(TravelTimeError error) {
        return toTestFailedError(error, error.getMessage());
    }

    public static <A> Either<TestFailedError, A> mapTravelTimeError(Either<TravelTimeError, A> errorOrA) {
        return errorOrA.mapLeft(TestFailedError::toTestFailedError);
    }

    public static <A> Either<TestFailedError, A> attempt(CheckedFunction0<A> supplier, String failureMessage) {
        return Try.of(supplier).toEither().mapLeft(e -> new TestFailedError(failureMessage, e));
    }

    public TestFailedError(String message) {
        super(message);
    }

    public TestFailedError(String message, Throwable cause) {
        super(message, cause);
    }
}
