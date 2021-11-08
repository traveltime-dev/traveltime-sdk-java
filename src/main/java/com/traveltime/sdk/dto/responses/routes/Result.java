package com.traveltime.sdk.dto.responses.routes;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Result {
    String searchId;
}
