package com.traveltime.sdk.dto.requests.timemapfast;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.transportationfast.Transportation;
import com.traveltime.sdk.dto.requests.timemapfast.levelofdetail.LevelOfDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class OneToMany {
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
}
