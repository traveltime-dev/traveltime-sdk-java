package com.traveltime.sdk.dto.responses.postcodes;

import jakarta.validation.Valid;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
    @NonNull
    String searchId;
    @Valid
    @NonNull
    List<Postcode> postcodes;
}
