package com.traveltime.sdk.dto.requests.proto;

import lombok.Value;

public interface Country {
    String getValue();

    Country AUSTRALIA = Countries.AUSTRALIA;
    Country AUSTRIA = Countries.AUSTRIA;
    Country BELGIUM = Countries.BELGIUM;
    Country CANADA = Countries.CANADA;
    Country DENMARK = Countries.DENMARK;
    Country FINLAND = Countries.FINLAND;
    Country FRANCE = Countries.FRANCE;
    Country GERMANY = Countries.GERMANY;
    Country GREECE = Countries.GREECE;
    Country HUNGARY = Countries.HUNGARY;
    Country INDIA = Countries.INDIA;
    Country INDONESIA = Countries.INDONESIA;
    Country IRELAND = Countries.IRELAND;
    Country ITALY = Countries.ITALY;
    Country JAPAN = Countries.JAPAN;
    Country LATVIA = Countries.LATVIA;
    Country LITHUANIA = Countries.LITHUANIA;
    Country NETHERLANDS = Countries.NETHERLANDS;
    Country NEW_ZEALAND = Countries.NEW_ZEALAND;
    Country NORWAY = Countries.NORWAY;
    Country PHILIPPINES = Countries.PHILIPPINES;
    Country POLAND = Countries.POLAND;
    Country PORTUGAL = Countries.PORTUGAL;
    Country ROMANIA = Countries.ROMANIA;
    Country SINGAPORE = Countries.SINGAPORE;
    Country SOUTH_AFRICA = Countries.SOUTH_AFRICA;
    Country SPAIN = Countries.SPAIN;
    Country SWEDEN = Countries.SWEDEN;
    Country SWITZERLAND = Countries.SWITZERLAND;
    Country UNITED_KINGDOM = Countries.UNITED_KINGDOM;
    Country UNITED_STATES = Countries.UNITED_STATES;

    @Value
    class Custom implements Country {
        String value;
    }

    static Country[] values() {
        return Countries.values();
    }
}
