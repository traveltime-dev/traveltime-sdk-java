package com.traveltime.sdk.dto.requests.timemapfast;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Snapping;
import com.traveltime.sdk.dto.common.levelofdetail.LevelOfDetail;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ManyToOne {
    @NonNull
    String id;

    @NonNull
    Coordinates coords;

    @NonNull
    String arrivalTimePeriod;

    @NonNull
    Integer travelTime;

    @NonNull
    Transportation transportation;

    LevelOfDetail levelOfDetail;
    Snapping snapping;
}
