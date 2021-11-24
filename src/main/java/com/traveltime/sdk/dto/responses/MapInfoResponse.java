package com.traveltime.sdk.dto.responses;


import com.traveltime.sdk.dto.responses.mapinfo.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class MapInfoResponse {
    List<Map> maps;
}
