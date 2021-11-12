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
public class Postcode {
    @NonNull
    String code;
    @Valid
    @NonNull
    Iterable<Property> properties;
}
