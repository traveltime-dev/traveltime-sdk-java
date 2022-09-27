package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.geocoding.Properties;
import com.traveltime.sdk.dto.responses.geojson.FeatureCollection;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.geojson.Point;

@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GeocodingResponse extends FeatureCollection<Point, Properties> {
}
