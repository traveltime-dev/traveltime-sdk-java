package com.traveltime.sdk.dto.requests.proto;

import lombok.Value;

public interface Country {
    String getValue();

    Country NETHERLANDS = Countries.NETHERLANDS;
    Country AUSTRIA = Countries.AUSTRIA;
    Country UNITED_KINGDOM = Countries.UNITED_KINGDOM;
    Country BELGIUM = Countries.BELGIUM;
    Country GERMANY = Countries.GERMANY;
    Country FRANCE = Countries.FRANCE;
    Country IRELAND = Countries.IRELAND;
    Country LITHUANIA = Countries.LITHUANIA;
    Country UNITED_STATES = Countries.UNITED_STATES;
    Country SOUTH_AFRICA = Countries.SOUTH_AFRICA;
    Country ROMANIA = Countries.ROMANIA;
    Country PORTUGAL = Countries.PORTUGAL;
    Country PHILIPPINES = Countries.PHILIPPINES;
    Country NEW_ZEALAND = Countries.NEW_ZEALAND;
    Country NORWAY = Countries.NORWAY;
    Country LATVIA = Countries.LATVIA;
    Country JAPAN = Countries.JAPAN;
    Country INDIA = Countries.INDIA;
    Country INDONESIA = Countries.INDONESIA;
    Country HUNGARY = Countries.HUNGARY;
    Country GREECE = Countries.GREECE;
    Country FINLAND = Countries.FINLAND;
    Country DENMARK = Countries.DENMARK;
    Country CANADA = Countries.CANADA;
    Country AUSTRALIA = Countries.AUSTRALIA;
    Country SINGAPORE = Countries.SINGAPORE;
    Country SWITZERLAND = Countries.SWITZERLAND;
    Country SPAIN = Countries.SPAIN;
    Country ITALY = Countries.ITALY;
    Country POLAND = Countries.POLAND;
    Country SWEDEN = Countries.SWEDEN;

    @Value
    class Custom implements Country {
        String value;
    }

    static Country[] values() {
        return Countries.values();
    }
}
