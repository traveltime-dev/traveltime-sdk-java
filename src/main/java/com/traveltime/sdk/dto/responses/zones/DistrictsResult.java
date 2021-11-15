package com.traveltime.sdk.dto.responses.zones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class DistrictsResult {
    @NonNull
    String searchId;
    @NonNull
    List<Zone> districts;
}
