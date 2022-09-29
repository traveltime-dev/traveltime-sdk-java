package com.traveltime.sdk.dto.responses.proto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProtoResult {
    @NonNull
    Integer travelTime;

    @NonNull
    String destinationId;
}
