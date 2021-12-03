package com.traveltime.sdk.dto.requests.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Intersection {
    @NonNull
    String id;
    @NonNull
    List<String> searchIds;
}
