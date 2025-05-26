package com.traveltime.sdk.dto.common;

import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Represents configuration for limiting the number of transfers in a public transport route.
 * <p>
 * Features:
 * - Enable or disable a limit on transfers.
 * - Specify a positive integer as the maximum allowable number of transfers.
 */
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class MaxChanges {

    /**
     * Indicates whether the limitation on the maximum number of transfers in a journey is enabled or disabled.
     * When true, the limit is enforced based on a specified value.
     * When false, there is no restriction on the number of transfers allowed.
     */
    @NonNull
    Boolean enabled;

    /**
     * Specifies the maximum allowable number of transfers in a journey.
     * The limit must always be a positive integer greater than zero.
     */
    @NonNull
    @Positive(message = "limit must be greater than 0")
    Integer limit;
}
