package com.traveltime.sdk.exceptions;

public class RequestValidationException extends Exception {
    public RequestValidationException(Error errorMessage) {
        super(errorMessage);
    }
}
