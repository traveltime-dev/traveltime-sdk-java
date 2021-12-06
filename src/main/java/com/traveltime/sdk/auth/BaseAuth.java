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
public class BaseAuth implements TravelTimeCredentials {
    @NonNull
    String username;
    @NonNull
    String password;

    @Override
    public Headers getHeaders() {
        return Headers.of("Authorization", Credentials.basic(username, password));
    }
}
