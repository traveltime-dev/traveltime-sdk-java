package com.traveltime.sdk.dto.responses.errors;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class XmlError implements TravelTimeError {
    @NonNull
    Throwable cause;

    @Override
    public Option<Throwable> retrieveCause() {
        return Option.of(cause);
    }

    @Override
    public String getMessage() {
        return cause.getMessage();
    }
}
