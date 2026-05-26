package com.traveltime.sdk.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.responses.TimeMapWktResponse;
import com.traveltime.sdk.utils.AcceptType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeMapWktRequest extends BaseTimeMapRequest<TimeMapWktResponse> {

    @Builder.Default
    @Getter
    boolean withHoles = true;

    @Override
    protected AcceptType acceptType() {
        return withHoles ? AcceptType.APPLICATION_WKT_JSON : AcceptType.APPLICATION_WKT_NO_HOLES_JSON;
    }

    @Override
    public Class<TimeMapWktResponse> responseType() {
        return TimeMapWktResponse.class;
    }
}
