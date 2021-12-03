package com.traveltime.sdk.dto.responses.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProperties {
    Boolean isOnlyWalking;
    List<Agency> agencies;
}
