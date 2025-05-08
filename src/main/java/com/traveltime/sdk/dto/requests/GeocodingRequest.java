package com.traveltime.sdk.dto.requests;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.timemap.Rectangle;
import com.traveltime.sdk.utils.QueryElement;
import com.traveltime.sdk.utils.Utils;
import io.vavr.control.Either;
import java.util.List;
import lombok.*;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GeocodingRequest extends TravelTimeRequest<GeocodingResponse> {
    @NonNull
    String query;

    @Singular
    List<String> withinCountries;

    Integer limit;

    Boolean formatName;

    Boolean formatExcludeCountry;

    Rectangle bounds;

    private QueryElement getLimit() {
        return new QueryElement("limit", limit == null ? "" : limit.toString());
    }

    private QueryElement getBounds() {
        String value = bounds == null
                ? ""
                : bounds.getMinLat() + "," + bounds.getMinLng() + "," + bounds.getMaxLat() + "," + bounds.getMaxLng();
        return new QueryElement("bounds", value);
    }

    private QueryElement getFormatName() {
        return new QueryElement("format.name", formatName == null ? "" : formatName.toString());
    }

    private QueryElement getFormatExcludeCountry() {
        return new QueryElement(
                "format.exclude.country", formatExcludeCountry == null ? "" : formatExcludeCountry.toString());
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val builder = baseUri.newBuilder().addPathSegments("geocoding/search");
        val uri = Utils.withQuery(
                        builder,
                        new QueryElement("query", query),
                        combineCountries(withinCountries),
                        getLimit(),
                        getFormatName(),
                        getFormatExcludeCountry(),
                        getBounds())
                .build();
        return Either.right(createGetRequest(uri, credentials));
    }

    @Override
    public Class<GeocodingResponse> responseType() {
        return GeocodingResponse.class;
    }
}
