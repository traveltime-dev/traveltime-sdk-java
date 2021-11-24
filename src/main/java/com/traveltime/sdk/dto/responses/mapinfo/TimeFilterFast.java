package com.traveltime.sdk.dto.responses.mapinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@AllArgsConstructor
public class TimeFilterFast {
    @NonNull
    List<Period> periods;
}
