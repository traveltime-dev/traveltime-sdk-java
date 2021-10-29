package com.traveltime.sdk.dto.responses;

import lombok.*;

@Getter
@Builder
public class TravelTimeResponse<T> {
    String errorMessage;
    Integer httpCode;
    T parsedBody;
}
