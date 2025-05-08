package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Supported {
    @NonNull
    String searchType;

    @NonNull
    String transportationMode;
}
