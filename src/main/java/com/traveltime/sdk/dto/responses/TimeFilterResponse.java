package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilter.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterResponse {
  @NonNull List<Result> results;
}
