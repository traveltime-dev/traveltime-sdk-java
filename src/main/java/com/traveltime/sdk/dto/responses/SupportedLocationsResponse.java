package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.locations.Location;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
