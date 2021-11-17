package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.routes.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class RoutesResponse {
    @Valid
    @NonNull
    List<Result> results;
}