package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.responses.TimeMapResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class TimeMapRequest extends BaseTimeMapRequest<TimeMapResponse> {

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_JSON;
    }

    @Override
    public Class<TimeMapResponse> responseType() {
        return TimeMapResponse.class;
    }
}
