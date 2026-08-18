package com.traveltime.sdk.dto.responses.geohash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
    String searchId;
    List<Cell> cells;
}
