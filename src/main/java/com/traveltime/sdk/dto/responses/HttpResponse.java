package com.traveltime.sdk.dto.responses;

import lombok.*;

@Getter
@Builder
public class HttpResponse<T> {
    String errorMessage;
    int httpCode;
    T parsedBody;
}
