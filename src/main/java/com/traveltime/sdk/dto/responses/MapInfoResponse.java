package com.traveltime.sdk.dto.responses;

import com.traveltime.sdk.dto.responses.mapinfo.Map;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
public class MapInfoResponse {
    List<Map> maps;
}
