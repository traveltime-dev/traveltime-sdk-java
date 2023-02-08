package com.traveltime.sdk.dto.requests.proto;

import lombok.Value;

public interface Country {
    String getValue();

    Country NETHERLANDS = Countries.NETHERLANDS;
    Country AUSTRIA = Countries.AUSTRIA;
    Country BELGIUM = Countries.BELGIUM;
    Country GERMANY = Countries.GERMANY;
    Country FRANCE = Countries.FRANCE;
    Country IRELAND = Countries.IRELAND;
    Country LITHUANIA = Countries.LITHUANIA;
    Country LATVIA = Countries.LATVIA;
    Country UNITED_KINGDOM = Countries.UNITED_KINGDOM;

    @Value
    class Custom implements Country {
        String value;
    }

    static Country[] values() {
        return Countries.values();
    }
}
