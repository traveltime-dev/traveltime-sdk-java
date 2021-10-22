package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
   use = JsonTypeInfo.Id.NAME,
   property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PublicTransport.class, name = "public_transport"),
    @JsonSubTypes.Type(value = Bus.class, name = "bus"),
    @JsonSubTypes.Type(value = Driving.class, name = "driving")
})
public interface Transportation { }