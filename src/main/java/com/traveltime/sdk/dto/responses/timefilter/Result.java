package com.traveltime.sdk.dto.responses.timefilter;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
    String searchId;
    List<Location> locations;
    List<String> unreachable;
}
