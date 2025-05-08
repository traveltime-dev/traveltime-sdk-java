package com.traveltime.sdk.dto.requests.proto;

import lombok.Value;

public interface Country {
  String getValue();

  @Value
  class Custom implements Country {
    String value;
  }

  static Country[] values() {
    return Countries.values();
  }
}
