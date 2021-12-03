package com.traveltime.sdk.dto.responses.locations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    @NonNull
    String id;
    @NonNull
    String mapName;
    List<String> additionalMapNames;
}
