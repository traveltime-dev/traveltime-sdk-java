package com.traveltime.sdk.dto.responses.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
public class Result {
    String searchId;
    List<Shape> shapes;
    ResponseProperties properties;
}
