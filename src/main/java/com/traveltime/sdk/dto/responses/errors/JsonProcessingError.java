package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class JsonProcessingError implements TravelTimeError {
    @NonNull
    String errorMsg;

    @Override
    public String getMessage() {
        return errorMsg;
    }
}