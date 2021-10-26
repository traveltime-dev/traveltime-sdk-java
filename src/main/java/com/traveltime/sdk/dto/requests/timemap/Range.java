package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@Setter(AccessLevel.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Range {
    Boolean enabled;
    int width;
}
