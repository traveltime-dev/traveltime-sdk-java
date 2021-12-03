package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFast {
    @NonNull
    List<Period> periods;
}
