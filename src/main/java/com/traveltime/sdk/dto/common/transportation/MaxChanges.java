package com.traveltime.sdk.dto.common.transportation;

import jakarta.validation.constraints.Positive;
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
    @Positive
    Integer limit;
}
