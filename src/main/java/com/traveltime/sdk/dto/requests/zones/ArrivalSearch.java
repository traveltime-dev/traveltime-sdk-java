package com.traveltime.sdk.dto.requests.zones;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Date;
import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArrivalSearch {
    @NonNull
    String id;
    @NonNull
    Coordinates coords;
    @NonNull
    Transportation transportation;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    Date arrivalTime;
    @NonNull
    @Positive(message = "travelTime must be greater than 0")
    Integer travelTime;
    @NonNull
    Double reachablePostcodesThreshold;
    @NonNull
    List<Property> properties;
    @Valid
    FullRange range;
}
