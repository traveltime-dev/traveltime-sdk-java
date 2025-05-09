package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.requests.MapInfoRequest;
import com.traveltime.sdk.dto.responses.MapInfoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ResponseError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.XmlError;
import io.vavr.control.Either;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorTest {
    TravelTimeSDK sdk;

    @Before
    public void init() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials("wrong-api-id-does-not-exist-123456789", "wrong-api-key-12345678");
        sdk = new TravelTimeSDK(credentials);
    }

    @Test
    public void shouldDetectErrorForWrongCredentials() {
        MapInfoRequest request = new MapInfoRequest();
        Either<TravelTimeError, MapInfoResponse> response = sdk.send(request);
        Assert.assertTrue(response.isLeft());
        Assert.assertTrue(response.getLeft() instanceof ResponseError);
        ResponseError responseError = (ResponseError) response.getLeft();
        Assert.assertEquals(401, (int) responseError.getHttpStatus());
        Assert.assertTrue(responseError.retrieveCause().isEmpty());
    }

    @Test
    public void shouldRetrieveCauseForIOError() {
        String message = "Booom";
        Throwable cause = new IllegalStateException(message);
        IOError ioError = new IOError(cause, "some error occurred");
        Assert.assertFalse(ioError.retrieveCause().isEmpty());
        Assert.assertSame(message, ioError.retrieveCause().get().getMessage());
    }

    @Test
    public void shouldRetrieveCauseForXmlError() {
        String message = "Booom";
        Throwable cause = new IllegalStateException(message);
        XmlError xmlError = new XmlError(cause);
        Assert.assertFalse(xmlError.retrieveCause().isEmpty());
        Assert.assertSame(message, xmlError.retrieveCause().get().getMessage());
    }
}
