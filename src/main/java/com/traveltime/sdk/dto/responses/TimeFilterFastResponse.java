package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timefilterfast.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFastResponse {
  @NonNull List<Result> results;
}
