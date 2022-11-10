package com.traveltime.sdk.dto.responses.errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
@Getter
@AllArgsConstructor
@ToString
public class ResponseError implements TravelTimeError {
    @NonNull
    Integer httpStatus;
    @NonNull
    Integer errorCode;
    @NonNull
    String description;
    @NonNull
    String documentationLink;
    @NonNull
    Map<String, List<String>> additionalInfo;

    @Override
    @JsonIgnore
    public String getMessage() {
        return description;
    }
}
