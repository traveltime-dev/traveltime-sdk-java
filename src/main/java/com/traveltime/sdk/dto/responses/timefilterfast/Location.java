package com.traveltime.sdk.dto.responses.timefilterfast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class Location {
    @NonNull
    String id;
    @NonNull
    Properties properties;
}
