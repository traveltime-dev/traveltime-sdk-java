package com.traveltime.sdk;


import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterProtoRequest;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class TimeFilterProtoTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK(System.getenv("PROTO_USERNAME"), System.getenv("PROTO_PASSWORD"));
    }

    @Test
    public void shouldSendTimeFilterProtoRequest() {
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        OneToMany oneToMany = new OneToMany(origin, destinations, Transportation.DRIVING_FERRY, 7200);
        TimeFilterProtoRequest request = new TimeFilterProtoRequest(oneToMany);

        Either<TravelTimeError, TimeFilterProtoResponse> response = sdk.sendProto(request);
        Assert.assertTrue(response.isRight());
    }
}
