package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.responses.TimeMapGeoJsonResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class TimeMapGeoJsonRequest extends BaseTimeMapRequest<TimeMapGeoJsonResponse> {

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_GEO_JSON;
    }

    @Override
    public Class<TimeMapGeoJsonResponse> responseType() {
        return TimeMapGeoJsonResponse.class;
    }
}
