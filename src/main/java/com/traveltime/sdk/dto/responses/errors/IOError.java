package com.traveltime.sdk.dto.responses.errors;

import com.traveltime.sdk.utils.ErrorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class IOError implements TravelTimeError {
    @NonNull
    Throwable cause;

    @NonNull
    String errorMessage;

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "IOError( " + getMessage() + ")\n" +
                ErrorUtils.printableStackTrace(getCause());
    }
}