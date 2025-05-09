package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Map {
    @NonNull
    String name;

    @NonNull
    Feature features;
}
