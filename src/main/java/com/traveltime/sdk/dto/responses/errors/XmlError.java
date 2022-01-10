package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class XmlError implements TravelTimeError {
    @NonNull
    Throwable cause;

    @Override
    public String getMessage() {
        return cause.getMessage();
    }
}
