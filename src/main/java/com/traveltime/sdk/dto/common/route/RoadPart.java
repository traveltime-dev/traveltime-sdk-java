package com.traveltime.sdk.dto.common.route;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
// TODO: Lack of documentation
public class RoadPart implements Part {
    @NonNull
    Integer id;

    @NonNull
    // TODO: Convert to Enum
    String mode;

    @NonNull
    String directions;

    @NonNull
    @Positive(message = "distance must be greater than 0")
    Integer distance;

    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;

    @NonNull
    List<Coordinates> coords;

    String road;
    String turn;
}
