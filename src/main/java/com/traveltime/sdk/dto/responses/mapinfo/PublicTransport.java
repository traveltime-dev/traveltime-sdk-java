package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.OffsetDateTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class PublicTransport {
    @NonNull
    OffsetDateTime dateStart;
    @NonNull
    OffsetDateTime dateEnd;
}
