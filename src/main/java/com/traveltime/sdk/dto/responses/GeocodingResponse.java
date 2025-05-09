package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.geojson.FeatureCollection;
import com.traveltime.sdk.dto.responses.geocoding.Properties;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.geojson.Point;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class GeocodingResponse extends FeatureCollection<Point, Properties> {}
