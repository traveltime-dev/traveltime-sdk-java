package com.traveltime.sdk.dto.responses.timemap;

import com.traveltime.sdk.dto.common.Shape;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
    String searchId;
    List<Shape> shapes;
    ResponseProperties properties;
}
