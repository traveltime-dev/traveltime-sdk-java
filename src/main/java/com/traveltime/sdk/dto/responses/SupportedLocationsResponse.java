package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.locations.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class SupportedLocationsResponse {
    @NonNull
    Iterable<Location> locations;
    @NonNull
    Iterable<String> unsupportedLocations;
}
