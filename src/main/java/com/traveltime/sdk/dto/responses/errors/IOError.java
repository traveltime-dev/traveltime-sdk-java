package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class IOError implements TravelTimeError {
    @NonNull
    Throwable cause;

    @NonNull
    String errorMessage;

    @Override
    public String getMessage() {
        return cause.getMessage();
    }
}