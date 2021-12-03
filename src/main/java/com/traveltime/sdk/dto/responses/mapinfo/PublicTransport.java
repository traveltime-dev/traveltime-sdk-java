package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class PublicTransport {
    @NonNull
    LocalDateTime dateStart;
    @NonNull
    LocalDateTime dateEnd;
}
