package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.timemap.BoxesResult;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeMapBoxesResponse {
  @NonNull List<BoxesResult> results;
}
