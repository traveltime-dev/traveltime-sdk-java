package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.dto.common.Coordinates;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static List<TimeFilterFastProtoRequest> split(TimeFilterFastProtoRequest request, int batchSizeHint) {
        /* Naively splitting requests may lead to situations where the last request is very small and inefficient.
         * We adjust the batch sizes to never have a situation where a batch is smaller than (loadFactor * batchSizeHint).
         */

        float loadFactor = 0.1f;
        List<Coordinates> originalDestinations = request.getOneToMany().getDestinationCoordinates();
        if (originalDestinations.size() <= batchSizeHint * (loadFactor + 1)) {
            return Collections.singletonList(request);
        } else {
            int batchCount = originalDestinations.size() / batchSizeHint;
            if (originalDestinations.size() % batchSizeHint > 0 &&
                    originalDestinations.size() % batchSizeHint < loadFactor * batchSizeHint) {
                batchCount -= 1;
            }
            int batchSize = (int) Math.ceil((float) originalDestinations.size() / batchCount);

            ArrayList<TimeFilterFastProtoRequest> batchedDestinations = new ArrayList<>(batchCount);

            for (int offset = 0; offset < originalDestinations.size(); offset += batchSize) {
                List<Coordinates> batch = originalDestinations.subList(offset, Math.min(offset + batchSize, originalDestinations.size()));
                batchedDestinations.add(
                        request.withOneToMany(
                                request.getOneToMany().withDestinationCoordinates(batch)
                        )
                );
            }
            return batchedDestinations;
        }
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
