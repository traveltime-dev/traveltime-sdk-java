package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.timemap.Rectangle;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.Request;

import java.net.URI;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull
    String query;

    List<String> withinCountries;

    Integer limit;

    Boolean formatName;

    Boolean formatExcludeCountry;

    Rectangle bounds;

    private String getLimit() {
        if (limit == null)
            return "";
        else
            return "&limit=" + limit;
    }

    private String getBounds() {
        if(bounds == null)
            return "";
        else
            return "&bounds="
                    + bounds.getMinLat()
                    + ","
                    + bounds.getMinLng()
                    + ","
                    + bounds.getMaxLat()
                    + ","
                    + bounds.getMaxLng();
    }

    private String getFormatName() {
        if(formatName == null)
            return "";
        else
            return "&format.name=" + formatName;
    }

    private String getFormatExcludeCountry() {
        if(formatExcludeCountry == null)
            return "";
        else
            return "&format.exclude.country=" + formatExcludeCountry;
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String uri = baseUri.resolve("/geocoding/search")
                + "?query=" + query
                + combineCountries(withinCountries)
                + getLimit()
                + getFormatName()
                + getFormatExcludeCountry()
                + getBounds();
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}

