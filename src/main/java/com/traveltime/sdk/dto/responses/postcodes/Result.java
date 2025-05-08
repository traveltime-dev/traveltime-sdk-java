package com.traveltime.sdk.dto.responses.postcodes;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
  @NonNull String searchId;
  @Valid @NonNull List<Postcode> postcodes;
}
