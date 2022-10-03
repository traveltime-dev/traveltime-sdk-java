package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.dto.responses.ProtoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ProtoError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.net.URI;

public abstract class ProtoRequest {
    private static final String IO_PROTO_ERROR = "Something went wrong when parsing proto response: ";

    public abstract Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials);

    protected Request createProtobufRequest(
        TravelTimeCredentials credentials,
        String url,
        byte[] requestBody
    ) {
        return new Request.Builder()
            .url(url)
            .headers(credentials.getBasicCredentialsHeaders())
            .addHeader("Content-Type", AcceptType.APPLICATION_OCTET_STREAM.getValue())
            .post(RequestBody.create(requestBody))
            .build();
    }

    private Either<TravelTimeError, ProtoResponse> parseResponse(TimeFilterFastResponseOuterClass.TimeFilterFastResponse response) {
        if(response.hasError()) {
            return Either.left(new ProtoError(response.getError().toString()));
        } else {
            return Either.right(new ProtoResponse(response.getProperties().getTravelTimesList()));
        }
    }

    public Either<TravelTimeError, ProtoResponse> parseBytes(byte[] body) {
        return Try
            .of(() -> TimeFilterFastResponseOuterClass.TimeFilterFastResponse.parseFrom(body))
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_PROTO_ERROR + cause.getMessage()))
            .flatMap(this::parseResponse);
    }
}
