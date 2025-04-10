package com.traveltime.sdk.dto.responses.errors;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ProtoError implements TravelTimeError {
    @NonNull
    String errorCode;
    @NonNull
    String errorMsg;
    @NonNull
    String errorDetails;
    int httpStatusCode;

    @Override
    public Option<Throwable> retrieveCause() { return Option.none(); }

    @Override
    public String getMessage() {
        return errorMsg;
    }

    public String getCode() {
        return errorCode;
    }

    public String getDetails() {
        return errorDetails;
    }

    public int getStatusCode() {
        return httpStatusCode;
    }
}
