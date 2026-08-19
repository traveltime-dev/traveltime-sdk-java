package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.requests.timemap.*;
import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public abstract class BaseTimeMapRequest<T> extends BaseTravelTimePostRequest<T> {

    @Valid
    @Singular
    List<DepartureSearch> departureSearches;

    @Valid
    @Singular
    List<ArrivalSearch> arrivalSearches;

    @Singular
    List<Intersection> intersections;

    @Singular
    List<Union> unions;

    @Override
    protected String endpoint() {
        return "time-map";
    }
}
