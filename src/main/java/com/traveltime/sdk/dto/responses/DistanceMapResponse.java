package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.distancemap.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class DistanceMapResponse {
    @NonNull
    List<Result> results;
}
