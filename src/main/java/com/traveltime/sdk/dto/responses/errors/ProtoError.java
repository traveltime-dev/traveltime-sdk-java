package com.traveltime.sdk.dto.responses.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ProtoError implements TravelTimeError{
    @NonNull
    String errorMsg;
}
