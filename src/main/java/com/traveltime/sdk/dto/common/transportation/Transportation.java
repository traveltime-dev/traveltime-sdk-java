package com.traveltime.sdk.dto.common.transportation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = PublicTransport.class, name = "public_transport"),
  @JsonSubTypes.Type(value = Bus.class, name = "bus"),
  @JsonSubTypes.Type(value = Driving.class, name = "driving"),
  @JsonSubTypes.Type(value = Train.class, name = "train"),
  @JsonSubTypes.Type(value = Coach.class, name = "coach"),
  @JsonSubTypes.Type(value = Cycling.class, name = "cycling"),
  @JsonSubTypes.Type(value = Walking.class, name = "walking"),
  @JsonSubTypes.Type(value = Ferry.class, name = "ferry"),
  @JsonSubTypes.Type(value = CyclingFerry.class, name = "cycling+ferry"),
  @JsonSubTypes.Type(value = DrivingFerry.class, name = "driving+ferry"),
  @JsonSubTypes.Type(value = DrivingTrain.class, name = "driving+train"),
})
public interface Transportation {}
