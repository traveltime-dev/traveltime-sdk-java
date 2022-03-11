package com.traveltime.sdk.dto.responses.postcodes;

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
    @NonNull
    List<Postcode> postcodes;
}
