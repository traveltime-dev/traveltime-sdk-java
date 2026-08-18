package com.traveltime.sdk.dto.responses.geohash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

/**
 * Travel times to a geohash cell, in seconds. Each is null unless requested.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Properties {
    Integer min;
    Integer max;
    Integer mean;
}
