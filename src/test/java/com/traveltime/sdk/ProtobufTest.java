package com.traveltime.sdk;


import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass.TimeFilterFastResponse;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.TimeFilterProtoRequest;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

public class ProtobufTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        sdk = new TravelTimeSDK("???","???");
    }

    @Test
    public void shouldSendProtobufRequest(){
        Coordinates origin = new Coordinates(51.425709, -0.122061);
        List<Coordinates> destinations = Collections.singletonList(new Coordinates(51.348605, -0.314783));
        OneToMany oneToMany = new OneToMany(origin, destinations, Transportation.DRIVING_FERRY, 7200);
        TimeFilterProtoRequest request = new TimeFilterProtoRequest(oneToMany);

        Either<TravelTimeError, TimeFilterProtoResponse> response = sdk.send(request);

        Assert.assertTrue(response.isRight());

    }
}
