package com.traveltime.sdk.dto.common.transportation;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class MaxChanges {
    @NonNull
    Boolean enabled;
    @NonNull
    Integer limit;
}
