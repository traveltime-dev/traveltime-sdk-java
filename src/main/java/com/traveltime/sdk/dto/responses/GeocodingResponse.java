package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.geocoding.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class GeocodingResponse {

    @NonNull
    String type;

    @NonNull
    List<Feature> features;
}
