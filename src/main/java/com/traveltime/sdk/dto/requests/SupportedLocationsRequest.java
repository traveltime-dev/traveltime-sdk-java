package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.responses.SupportedLocationsResponse;
import com.traveltime.sdk.utils.AcceptType;
import java.util.List;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SupportedLocationsRequest extends BaseTravelTimePostRequest<SupportedLocationsResponse> {
    @NonNull
    @Singular
    List<Location> locations;

    @Override
    protected String endpoint() {
        return "supported-locations";
    }

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<SupportedLocationsResponse> responseType() {
        return SupportedLocationsResponse.class;
    }
}
