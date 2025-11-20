package com.traveltime.sdk.server_tests.dto.details;

import com.traveltime.sdk.server_tests.values.SessionId;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class SessionCreated {
    @NonNull
    SessionId sessionId;
}
