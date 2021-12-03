package com.traveltime.sdk.dto.responses.zones;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class SectorsResult {
    @NonNull
    String searchId;
    @NonNull
    List<Zone> sectors;
}
