package com.traveltime.sdk.dto.responses.errors;

import io.vavr.control.Option;

public interface TravelTimeError {
    Option<Throwable> retrieveCause();

    String getMessage();
}
