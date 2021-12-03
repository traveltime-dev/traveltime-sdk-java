package com.traveltime.sdk.dto.responses.routes;

import jakarta.validation.Valid;
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
    @Valid
    @NonNull
    List<Property> properties;
}
