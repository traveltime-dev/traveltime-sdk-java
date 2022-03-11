package com.traveltime.sdk.dto.responses.routes;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Location {
    @NonNull
    String id;
    @NonNull
    List<Property> properties;
}
