package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@SuperBuilder
public class PublicTransport implements Transportation {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    MaxChanges maxChanges;
}
