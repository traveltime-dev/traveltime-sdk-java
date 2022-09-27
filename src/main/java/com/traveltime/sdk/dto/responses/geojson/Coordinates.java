package com.traveltime.sdk.dto.responses.geojson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "lng", "lat" })
public class Coordinates {
    @NonNull
    Double lat;
    @NonNull
    Double lng;
}
