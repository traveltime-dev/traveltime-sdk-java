package com.traveltime.sdk.dto.responses;

import lombok.*;

@Getter
@Builder
public class HttpResponse<T> {
    String errorMessage;
    @NonNull
    int httpCode;
    T parsedBody;
}
