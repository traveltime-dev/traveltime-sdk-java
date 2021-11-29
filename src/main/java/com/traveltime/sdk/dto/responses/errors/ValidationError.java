package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ValidationError implements TravelTimeError {
    @NonNull
    String errorMsg;

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
