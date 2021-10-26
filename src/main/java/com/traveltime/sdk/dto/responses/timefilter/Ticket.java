package com.traveltime.sdk.dto.responses.timefilter;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class Ticket {
    String type;
    Double price;
    String currency; // will be enum
}
