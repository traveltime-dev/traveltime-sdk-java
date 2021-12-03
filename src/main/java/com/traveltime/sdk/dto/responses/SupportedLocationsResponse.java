package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.locations.Location;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class SupportedLocationsResponse {
    @NonNull
    List<Location> locations;
    @NonNull
    List<String> unsupportedLocations;
}
