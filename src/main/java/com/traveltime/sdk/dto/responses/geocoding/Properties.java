package com.traveltime.sdk.dto.responses.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {

    @NonNull
    String name;

    @NonNull
    String label;

    Double score;
    String category;
    String type;
    String houseNumber;
    String street;
    String region;
    String regionCode;
    String neighbourhood;
    String county;
    String macroregion;
    String city;
    String country;
    String countryCode;
    String localAdmin;
    String continent;
    String postcode;

    com.traveltime.sdk.dto.responses.mapinfo.Feature features;
}
