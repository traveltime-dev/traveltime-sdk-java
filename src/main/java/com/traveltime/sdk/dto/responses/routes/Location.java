package com.traveltime.sdk.dto.responses.routes;

import jakarta.validation.Valid;
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
public class Location {
    @NonNull
    String id;
    @Valid
    @NonNull
    List<Property> properties;
}
