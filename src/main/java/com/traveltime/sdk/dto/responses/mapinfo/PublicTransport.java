package com.traveltime.sdk.dto.responses.mapinfo;

import java.time.OffsetDateTime;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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
