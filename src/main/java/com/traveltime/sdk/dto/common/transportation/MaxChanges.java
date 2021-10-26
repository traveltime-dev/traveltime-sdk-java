package com.traveltime.sdk.dto.common.transportation;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder(builderMethodName = "internalBuilder")
@AllArgsConstructor
public class MaxChanges {
    @NonNull
    Boolean enabled;
    @NonNull
    @Positive(message = "limit must be greater than 0")
    Integer limit;

    public static MaxChangesBuilder builder(Boolean enabled, int limit) {
        return internalBuilder().enabled(enabled).limit(limit);
    }
}
