package com.traveltime.sdk.dto.responses.mapinfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feature {
    @NonNull
    List<String> crossCountryModes;

    PublicTransport publicTransport;
    TimeFilterFast timeFilterFast;
    Boolean fares;
    Boolean postcodes;
}
