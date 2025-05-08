package com.traveltime.sdk.dto.responses.zones;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Zone {
    @NonNull
    String code;

    @NonNull
    Properties properties;
}
