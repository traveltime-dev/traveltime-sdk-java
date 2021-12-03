package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Location {
    String id;
    List<Property> properties;
}
