package com.traveltime.sdk.dto.responses.timemap;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Agency {
    @NonNull
    String name;
    @NonNull
    List<String> modes;
}
