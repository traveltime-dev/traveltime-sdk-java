package com.traveltime.sdk.dto.requests.timemap;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Union {
    @NonNull
    String id;
    @NonNull
    @Singular
    List<String> searchIds;
}
