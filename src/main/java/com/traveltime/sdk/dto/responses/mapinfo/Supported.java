package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class Supported {
    @NonNull
    String searchType;
    @NonNull
    String transportationMode;
}
