package com.traveltime.sdk.dto.responses;

import lombok.*;

@Getter
@Builder
public class HttpResponse<T> {
    String errorMessage;
    Integer httpCode;
    T parsedBody;
}
