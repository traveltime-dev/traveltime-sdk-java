package com.traveltime.sdk.dto.responses.timefilterfast;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Result {
    @NonNull
    String searchId;

    @NonNull
    List<Location> locations;

    @NonNull
    List<String> unreachable;
}
