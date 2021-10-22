package com.traveltime.sdk.dto.common.transportation;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder
public class MaxChanges {
    @NonNull
    Boolean enabled;
    @NonNull
    Integer limit;
}
