package com.traveltime.sdk.server_tests.values;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;

@Value
public class SessionId {
    @JsonValue
    String value;

    @JsonCreator
    public SessionId(String value) { this.value = value; }
}
