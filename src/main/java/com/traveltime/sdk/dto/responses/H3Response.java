package com.traveltime.sdk.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.traveltime.sdk.dto.responses.h3.Result;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@JsonIgnoreProperties(ignoreUnknown = true)
@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class H3Response {
    @NonNull
    List<Result> results;
}
