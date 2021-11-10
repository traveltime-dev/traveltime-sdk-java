package com.traveltime.sdk.dto.responses.routes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class Result {
    @NonNull
    String searchId;
    @Valid
    @NonNull
    Iterable<Location> locations;
    @NonNull
    Iterable<String> unreachable;
}