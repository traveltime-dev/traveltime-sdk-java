package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.utils.AcceptType;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.List;

public abstract class ProtoRequest<T> {
    private static final String IO_PROTO_ERROR = "Something went wrong when parsing proto response: ";

    public abstract Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials);

    public abstract List<Coordinates> getDestinationCoordinates();

    public abstract List<ProtoRequest<T>> split(int batchSizeHint);

    public abstract T merge(List<T> responses);

    public abstract Either<TravelTimeError, T> parseBytes(byte[] body);

    protected Either<TravelTimeError, TimeFilterFastResponseOuterClass.TimeFilterFastResponse> getProtoResponse(byte[] body) {
        return Try
            .of(() -> TimeFilterFastResponseOuterClass.TimeFilterFastResponse.parseFrom(body))
            .toEither()
            .mapLeft(cause -> new IOError(cause, IO_PROTO_ERROR + cause.getMessage()));
    }

    protected Request createProtobufRequest(
        TravelTimeCredentials credentials,
        HttpUrl url,
        byte[] requestBody
    ) {
        return new Request.Builder()
            .url(url)
            .headers(credentials.getBasicCredentialsHeaders())
            .addHeader("Content-Type", AcceptType.APPLICATION_OCTET_STREAM.getValue())
            .addHeader("User-Agent", "Travel Time Java SDK")
            .post(RequestBody.create(requestBody))
            .build();
    }
}
