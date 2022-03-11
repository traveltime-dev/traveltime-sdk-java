package com.traveltime.sdk.dto.responses.postcodes;

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
    @NonNull
    List<Property> properties;
}
