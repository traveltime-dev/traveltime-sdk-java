package com.traveltime.sdk;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcceptType {
    APPLICATION_JSON("application/json"),
    APPLICATION_WKT_JSON("application/vnd.wkt+json"),
    APPLICATION_WKT_NO_HOLES_JSON("application/vnd.wkt-no-holes+json"),
    APPLICATION_GEO_JSON("application/geo+json"),
    APPLICATION_BOUNDING_BOXES_JSON("application/vnd.bounding-boxes+json"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private final String value;
}
