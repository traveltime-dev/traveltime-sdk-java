package com.traveltime.sdk.dto.responses.timemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProperties {

    /**
     * Indicates whether the response contains only walking segments, meaning no
     * alternative transportation modes (such as bus, train, or other public transport)
     * were available or used for the requested route.
     */
    Boolean isOnlyWalking;

    /**
     * List of transit agencies involved in providing the response.
     * Each agency represents a transportation service provider (e.g., bus companies, rail operators)
     */
    List<Agency> agencies;
}
