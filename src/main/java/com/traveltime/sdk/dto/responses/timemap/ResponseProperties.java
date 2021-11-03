package com.traveltime.sdk.dto.responses.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProperties {
    Boolean isOnlyWalking;
    Iterable<Agency> agencies;
}
