package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Countries implements Country {
    NETHERLANDS("nl"),
    AUSTRIA("at"),
    UNITED_KINGDOM("uk"),
    BELGIUM("be"),
    GERMANY("de"),
    FRANCE("fr"),
    IRELAND("ie"),
    LITHUANIA("lt"),
    UNITED_STATES("us"),
    SOUTH_AFRICA("za"),
    ROMANIA("ro"),
    PORTUGAL("pt"),
    PHILIPPINES("ph"),
    NEW_ZEALAND("nz"),
    NORWAY("no"),
    LATVIA("lv"),
    JAPAN("jp"),
    INDIA("in"),
    INDONESIA("id"),
    HUNGARY("hu"),
    GREECE("gr"),
    FINLAND("fi"),
    DENMARK("dk"),
    CANADA("ca"),
    AUSTRALIA("au"),
    SINGAPORE("sg"),
    SWITZERLAND("ch"),
    SPAIN("es"),
    ITALY("it"),
    POLAND("pl"),
    SWEDEN("se");

    private final String value;
}
