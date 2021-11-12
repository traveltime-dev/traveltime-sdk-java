package com.traveltime.sdk.dto.common.route;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StartEndPart.class, name = "start_end"),
    @JsonSubTypes.Type(value = BasicPart.class, name = "basic"),
    @JsonSubTypes.Type(value = RoadPart.class, name = "road"),
    @JsonSubTypes.Type(value = PublicTransportPart.class, name = "public_transport")
})
public interface Part { }