package com.traveltime.sdk.dto.requests.timemapfast;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.traveltime.sdk.dto.requests.timemapfast.transportation.*;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = PublicTransport.class, name = "public_transport"),
    @JsonSubTypes.Type(value = Driving.class, name = "driving"),
    @JsonSubTypes.Type(value = Cycling.class, name = "cycling"),
    @JsonSubTypes.Type(value = Walking.class, name = "walking"),
    @JsonSubTypes.Type(value = WalkingAndFerry.class, name = "walking+ferry"),
    @JsonSubTypes.Type(value = CyclingAndFerry.class, name = "cycling+ferry"),
    @JsonSubTypes.Type(value = DrivingAndFerry.class, name = "driving+ferry"),
    @JsonSubTypes.Type(value = DrivingAndPublicTransport.class, name = "driving+public_transport"),
})
public interface Transportation { }
