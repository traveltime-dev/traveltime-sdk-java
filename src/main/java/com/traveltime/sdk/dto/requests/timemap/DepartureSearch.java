package com.traveltime.sdk.dto.requests.timemap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;


@Getter
@Jacksonized
@Builder(builderMethodName = "internalBuilder")
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartureSearch {
    @NonNull
    String id;
    @NonNull
    Coordinates coords;
    @Valid
    @NonNull
    Transportation transportation;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date departureTime;
    @NonNull
    @Positive(message = "travelTime should be positive")
    Integer travelTime;
    @Valid
    Range range;

    public static DepartureSearchBuilder builder(
        String id,
        Coordinates coords,
        Transportation transportation,
        Date departureTime,
        Integer travelTime
    ) {
        return internalBuilder()
            .id(id)
            .coords(coords)
            .transportation(transportation)
            .departureTime(departureTime)
            .travelTime(travelTime);
    }
}