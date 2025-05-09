package com.traveltime.sdk.dto.responses.errors;

import io.vavr.control.Option;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProtoError implements TravelTimeError {
    int errorCode;

    @NonNull
    String errorMsg;

    String errorDetails;

    int httpStatusCode;

    @Override
    public Option<Throwable> retrieveCause() {
        return Option.none();
    }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    public int getCode() {
        return errorCode;
    }

    public Optional<String> getDetails() {
        return Optional.ofNullable(errorDetails);
    }

    public int getStatusCode() {
        return httpStatusCode;
    }
}
