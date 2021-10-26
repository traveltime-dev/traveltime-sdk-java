package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Result {
    String searchId;
    Iterable<Location> locations;
    Iterable<String> unreachable;
}
