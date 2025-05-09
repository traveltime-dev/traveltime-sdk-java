package com.traveltime.sdk.dto.responses.errors;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ValidationError implements TravelTimeError {
    @NonNull
    String errorMsg;

    @Override
    public Option<Throwable> retrieveCause() {
        return Option.none();
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }
}
