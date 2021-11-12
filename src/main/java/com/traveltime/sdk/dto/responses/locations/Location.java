package com.traveltime.sdk.dto.responses.locations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    @NonNull
    String id;
    @NonNull
    String mapName;
    Iterable<String> additionalMapNames;
}
