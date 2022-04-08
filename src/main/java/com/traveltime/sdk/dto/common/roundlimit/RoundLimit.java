package com.traveltime.sdk.dto.common.roundlimit;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Unlimited.class, name = "unlimited"),
    @JsonSubTypes.Type(value = Limit.class, name = "limit")
})
public interface RoundLimit {}
