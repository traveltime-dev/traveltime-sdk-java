package com.traveltime.sdk.dto.responses.zones;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class DistrictsResult {
    @NonNull
    String searchId;
    @NonNull
    List<Zone> districts;
}
