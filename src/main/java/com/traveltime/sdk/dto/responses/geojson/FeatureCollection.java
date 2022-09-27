package com.traveltime.sdk.dto.responses.geojson;

import lombok.*;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Value
@NonFinal
@SuperBuilder
@Jacksonized
public class FeatureCollection<G extends Geometry, P> {

    @NonNull
    String type;

    List<Feature<G, P>> features;
}
