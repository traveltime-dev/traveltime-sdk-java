package com.traveltime.sdk.dto.responses.postcodes;

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
    Iterable<Postcode> postcodes;
}