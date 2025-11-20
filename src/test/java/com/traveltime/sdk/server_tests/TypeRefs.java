package com.traveltime.sdk.server_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.traveltime.sdk.server_tests.dto.TestServerResponse;
import com.traveltime.sdk.server_tests.dto.details.NoDetails;

public final class TypeRefs {
    static final TypeReference<TestServerResponse<NoDetails>> noTestParams =
        new TypeReference<TestServerResponse<NoDetails>>() {};
}
