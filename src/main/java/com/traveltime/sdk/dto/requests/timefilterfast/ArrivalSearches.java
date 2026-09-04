package com.traveltime.sdk.dto.requests.timefilterfast;

import jakarta.validation.Valid;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class ArrivalSearches {
    @Valid
    @NonNull
    List<ManyToOne> manyToOne;

    @Valid
    @NonNull
    List<OneToMany> oneToMany;
}
