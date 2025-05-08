package com.traveltime.sdk.dto.geojson;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.*;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.geojson.GeoJsonObject;

@Value
@NonFinal
@SuperBuilder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeatureCollection<G extends GeoJsonObject, P> {

  @NonNull String type;

  String attribution;

  List<Feature<G, P>> features;
}
