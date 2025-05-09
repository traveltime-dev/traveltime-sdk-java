package com.traveltime.sdk.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import okhttp3.Credentials;
import okhttp3.Headers;

@Value
@Builder
@AllArgsConstructor
public class TravelTimeCredentials {
    /**
     * App ID for accessing TravelTime services. You can find your App ID at <a
     * href="https://account.traveltime.com/">account.traveltime.com</a>
     */
    @NonNull
    String appId;

    /**
     * Api key for accessing TravelTime services. You can find your Api key at <a
     * href="https://account.traveltime.com/">account.traveltime.com</a>
     */
    @NonNull
    String apiKey;

    public Headers getHeaders() {
        return Headers.of("X-Application-Id", appId, "X-Api-Key", apiKey);
    }

    public Headers getBasicCredentialsHeaders() {
        return Headers.of("Authorization", Credentials.basic(appId, apiKey));
    }
}
