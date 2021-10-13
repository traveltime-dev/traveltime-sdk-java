package com.traveltime.sdk.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Transportation {
    String type; // temporary hack
    @JsonInclude(JsonInclude.Include.NON_NULL)
    MaxChanges maxChanges;
}
