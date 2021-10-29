package com.traveltime.sdk.exceptions;

public class RequestValidationException extends Exception {
    public RequestValidationException(String errorMessage) {
        super(errorMessage);
    }
}
