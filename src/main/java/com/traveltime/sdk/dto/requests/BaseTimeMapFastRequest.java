package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.requests.timemapfast.ArrivalSearches;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public abstract class BaseTimeMapFastRequest<T> extends BaseTravelTimePostRequest<T> {

    @NonNull
    ArrivalSearches arrivalSearches;

    @Override
    protected String endpoint() {
        return "time-map/fast";
    }
}
