package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.utils.AcceptType;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@EqualsAndHashCode(callSuper = true)
public class TimeMapKmlRequest extends BaseTimeMapRequest<Kml> {

    @Override
    protected AcceptType acceptType() {
        return AcceptType.APPLICATION_KML;
    }

    @Override
    public Class<Kml> responseType() {
        return Kml.class;
    }
}
