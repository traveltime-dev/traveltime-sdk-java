package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.geocoding.Properties;
import com.traveltime.sdk.dto.responses.geojson.FeatureCollection;
import com.traveltime.sdk.dto.responses.geojson.Point;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GeocodingResponse extends FeatureCollection<Point, Properties> {
}