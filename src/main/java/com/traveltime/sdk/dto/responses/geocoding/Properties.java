package com.traveltime.sdk.dto.responses.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.traveltime.sdk.dto.responses.mapinfo.Feature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    String town;
    String district;
    String country;
    String countryCode;
    String localAdmin;
    String continent;
    String postcode;

    Feature features;
}
