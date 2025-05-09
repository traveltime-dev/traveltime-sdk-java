package com.traveltime.sdk.dto.responses.timemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class GeoJsonProperties {
    @NonNull
    String searchId;
}
