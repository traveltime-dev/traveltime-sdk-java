package com.traveltime.sdk.dto.responses.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Result {
    String searchId;
    Iterable<Shape> shapes;
    Object properties; //temporary hack
}
