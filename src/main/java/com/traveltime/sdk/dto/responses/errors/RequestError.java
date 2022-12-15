package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class RequestError implements TravelTimeError {
    @NonNull
    String errorMsg;

    @Override
    public String getMessage() {
        return errorMsg;
    }
}