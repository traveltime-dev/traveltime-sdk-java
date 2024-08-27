package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Countries implements Country {
    AUSTRALIA("au"),
    AUSTRIA("at"),
    BELGIUM("be"),
    CANADA("ca"),
    DENMARK("dk"),
    FINLAND("fi"),
    FRANCE("fr"),
    GERMANY("de"),
    GREECE("gr"),
    HUNGARY("hu"),
    INDIA("in"),
    INDONESIA("id"),
    IRELAND("ie"),
    ITALY("it"),
    JAPAN("jp"),
    LATVIA("lv"),
    LITHUANIA("lt"),
    NETHERLANDS("nl"),
    NEW_ZEALAND("nz"),
    NORWAY("no"),
    PHILIPPINES("ph"),
    POLAND("pl"),
    PORTUGAL("pt"),
    ROMANIA("ro"),
    SINGAPORE("sg"),
    SOUTH_AFRICA("za"),
    SPAIN("es"),
    SWEDEN("se"),
    SWITZERLAND("ch"),
    UNITED_KINGDOM("uk"),
    UNITED_STATES("us");

    private final String value;
}
