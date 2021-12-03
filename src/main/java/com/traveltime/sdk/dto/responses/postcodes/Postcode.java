package com.traveltime.sdk.dto.responses.postcodes;

import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Postcode {
    @NonNull
    String code;
    @Valid
    @NonNull
    List<Property> properties;
}
