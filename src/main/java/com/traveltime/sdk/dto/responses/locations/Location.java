package com.traveltime.sdk.dto.responses.locations;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
