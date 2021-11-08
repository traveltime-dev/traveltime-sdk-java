package com.traveltime.sdk.dto.requests.timefilter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.FullRange;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartureSearch {
    @NonNull
    String id;
    @NonNull
    String departureLocationId;
    @NonNull
    Iterable<String> arrivalLocationIds;
    @NonNull
    Transportation transportation;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date departureTime;
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;
    @NonNull
    Iterable<String> properties;
    @Valid
    FullRange range;


    public static DepartureSearchBuilder builder(
        String id,
        String departureLocationId,
        Iterable<String> arrivalLocationIds,
        Transportation transportation,
        Date departureTime,
        Integer travelTime,
        Iterable<String> properties
    ) {
        return internalBuilder()
            .id(id)
            .departureLocationId(departureLocationId)
            .arrivalLocationIds(arrivalLocationIds)
            .transportation(transportation)
            .departureTime(departureTime)
            .travelTime(travelTime)
            .properties(properties);
    }
}
