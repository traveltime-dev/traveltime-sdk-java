package com.traveltime.sdk.dto.responses.mapinfo;

import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class TimeFilterFast {
    @NonNull
    List<Period> periods;
}
