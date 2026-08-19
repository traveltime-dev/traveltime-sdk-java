package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.dto.responses.TimeMapFastBoxesResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class TimeMapFastBoxesRequest extends BaseTimeMapFastRequest<TimeMapFastBoxesResponse> {

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_BOUNDING_BOXES_JSON;
    }

    @Override
    public Class<TimeMapFastBoxesResponse> responseType() {
        return TimeMapFastBoxesResponse.class;
    }
}
