package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.responses.TimeMapFastWktResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapFastWktRequest extends BaseTimeMapFastRequest<TimeMapFastWktResponse> {

    @Builder.Default
    @Getter
    boolean withHoles = true;

    @Override
    protected AcceptType acceptType() {
        return withHoles ? AcceptType.APPLICATION_WKT_JSON : AcceptType.APPLICATION_WKT_NO_HOLES_JSON;
    }

    @Override
    public Class<TimeMapFastWktResponse> responseType() {
        return TimeMapFastWktResponse.class;
    }
}
