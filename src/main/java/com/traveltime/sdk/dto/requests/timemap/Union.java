package com.traveltime.sdk.dto.requests.timemap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Getter
@Jacksonized
@AllArgsConstructor
public class Union {
    @NonNull
    String id;
    @NonNull
    List<String> searchIds;
}
