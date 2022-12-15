package com.traveltime.sdk.dto.requests.protodistance;

import lombok.Value;

public interface Country {
    String getValue();

    Country IRELAND = Countries.IRELAND;
    Country UNITED_KINGDOM = Countries.UNITED_KINGDOM;

    @Value
    class Custom implements Country {
        String value;
    }

    static Country[] values() {
        return Countries.values();
    }
}
