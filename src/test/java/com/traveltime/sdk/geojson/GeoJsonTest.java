package com.traveltime.sdk.geojson;

import com.traveltime.sdk.Common;
import com.traveltime.sdk.dto.geojson.FeatureCollection;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.utils.JsonUtils;
import io.vavr.control.Either;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

public class GeoJsonTest {
  @Test
  public void shouldParseAllGeoJsonFiles() throws IOException {
    List<ImmutablePair<Class, String>> jsons =
        Arrays.asList(
            new ImmutablePair(FeatureCollection.class, "dto/responses/geojson/point.json"),
            new ImmutablePair(FeatureCollection.class, "dto/responses/geojson/line_string.json"),
            new ImmutablePair(FeatureCollection.class, "dto/responses/geojson/polygon.json"),
            new ImmutablePair(FeatureCollection.class, "dto/responses/geojson/multi_polygon.json"));

    for (ImmutablePair<Class, String> json : jsons) {
      String expectedContent = Common.readFile(json.getValue());
      Either<TravelTimeError, Object> fromJson = JsonUtils.fromJson(expectedContent, json.getKey());
      if (fromJson.isLeft()) {
        Assert.fail();
      } else {
        String result = JsonUtils.toJsonPretty(fromJson.get()).get();
        Assert.assertEquals(expectedContent, result);
      }
    }
  }
}
