package com.traveltime.sdk.dto.responses.routes;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
