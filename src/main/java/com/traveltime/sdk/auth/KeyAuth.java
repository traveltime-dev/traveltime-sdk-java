package com.traveltime.sdk.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import okhttp3.Headers;

@Value
@Builder
@AllArgsConstructor
public class KeyAuth implements TravelTimeCredentials {
    String appId;
    String apiKey;

    @Override
    public Headers getHeaders() {
        return Headers.of("X-Application-Id", appId, "X-Api-Key", apiKey);
    }
}
