package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.geojson.FeatureCollection;
import com.traveltime.sdk.dto.responses.timemap.ResponseProperties;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.geojson.MultiPolygon;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TimeMapGeoJsonResponse extends FeatureCollection<MultiPolygon, ResponseProperties> {
}
