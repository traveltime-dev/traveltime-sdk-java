package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.postcodes.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterPostcodesResponse {
  @NonNull List<Result> results;
}
