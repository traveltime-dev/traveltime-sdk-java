package com.traveltime.sdk.dto.responses;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class H3FastProtoResponse {
    /**
     * H3 cell indices in the same lowercase hex form the JSON /v4/h3 endpoint returns,
     * for example <tt>87194ad14ffffff</tt>.
     */
    @NonNull
    List<String> ids;

    @NonNull
    List<Integer> minTravelTimes;

    @NonNull
    List<Integer> maxTravelTimes;

    @NonNull
    List<Integer> meanTravelTimes;
}
