package com.traveltime.sdk.dto.responses.postcodes;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Postcode {
  @NonNull String code;
  @Valid @NonNull List<Property> properties;
}
