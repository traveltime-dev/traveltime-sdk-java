package com.traveltime.sdk.utils;

import lombok.Value;

@Value
public class QueryElement {
  String key;
  String value;

  public boolean isDefined() {
    return key.length() > 0 && value.length() > 0;
  }

  @Override
  public String toString() {
    return isDefined() ? key + "=" + value : "";
  }
}
