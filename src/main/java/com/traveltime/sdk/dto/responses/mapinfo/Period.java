package com.traveltime.sdk.dto.responses.mapinfo;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class Period {
    @NonNull
    String timePeriod;

    @NonNull
    List<Supported> supported;
}
