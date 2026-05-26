package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.responses.TimeMapFastGeoJsonResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class TimeMapFastGeoJsonRequest extends BaseTimeMapFastRequest<TimeMapFastGeoJsonResponse> {

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_GEO_JSON;
    }

    @Override
    public Class<TimeMapFastGeoJsonResponse> responseType() {
        return TimeMapFastGeoJsonResponse.class;
    }
}
