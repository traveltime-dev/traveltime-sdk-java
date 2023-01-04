package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.geojson.FeatureCollection;
import com.traveltime.sdk.dto.responses.timemapfast.ResponseProperties;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.geojson.MultiPolygon;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class TimeMapFastGeoJsonResponse extends FeatureCollection<MultiPolygon, ResponseProperties> {
}
