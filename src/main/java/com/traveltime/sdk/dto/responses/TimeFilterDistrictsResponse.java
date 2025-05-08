package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.zones.DistrictsResult;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterDistrictsResponse {
  @NonNull List<DistrictsResult> results;
}
