package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.routes.Result;
import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class RoutesResponse {
    @Valid
    @NonNull
    List<Result> results;
}
