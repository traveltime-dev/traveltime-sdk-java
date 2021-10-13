package com.traveltime.sdk.dto.responses.timefilter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Ticket {
    String type;
    Double price;
    String currency; // will be enum
}
