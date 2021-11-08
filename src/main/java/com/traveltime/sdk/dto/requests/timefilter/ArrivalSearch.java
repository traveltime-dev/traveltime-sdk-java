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
public class ArrivalSearch {
    @NonNull
    String id;
    @NonNull
    Iterable<String> departureLocationIds;
    @NonNull
    String arrivalLocationId;
    @NonNull
    Transportation transportation;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date arrivalTime;
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;
    @NonNull
    Iterable<String> properties;
    @Valid
    FullRange range;

    public static ArrivalSearchBuilder builder(
        String id,
        Iterable<String> departureLocationIds,
        String arrivalLocationId,
        Transportation transportation,
        Date arrivalTime,
        Integer travelTime,
        Iterable<String> properties
    ) {
        return internalBuilder()
            .id(id)
            .departureLocationIds(departureLocationIds)
            .arrivalLocationId(arrivalLocationId)
            .transportation(transportation)
            .arrivalTime(arrivalTime)
            .travelTime(travelTime)
            .properties(properties);
    }
}
