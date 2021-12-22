package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass.TimeFilterFastResponse;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ProtoError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.*;
import okhttp3.Request;

import java.net.URI;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastProtoRequest extends ProtoRequest<TimeFilterFastProtoResponse> {
    private final static String IO_PROTO_ERROR = "Something went wrong when parsing proto response: ";
    @NonNull
    OneToMany oneToMany;

    private byte[] createByteArray() {
        Coordinates origin = oneToMany.getOriginCoordinate();

        RequestsCommon.Coords departure = RequestsCommon
            .Coords
            .newBuilder()
            .setLat(origin.getLat().floatValue())
            .setLng(origin.getLng().floatValue())
            .build();

        RequestsCommon.Transportation transportation = RequestsCommon
            .Transportation
            .newBuilder()
            .setTypeValue(oneToMany.getTransportation().getCode())
            .build();

        TimeFilterFastRequest.OneToMany.Builder oneToManyBuilder = TimeFilterFastRequest
            .OneToMany
            .newBuilder()
            .setDepartureLocation(departure)
            .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
            .setTransportation(transportation)
            .setTravelTime(oneToMany.getTravelTime());

        double mult = Math.pow(10, 5);
        for(Coordinates dest : oneToMany.getDestinationCoordinates()) {
            oneToManyBuilder.addLocationDeltas((int)Math.round((dest.getLat() - origin.getLat()) * mult));
            oneToManyBuilder.addLocationDeltas((int)Math.round((dest.getLng() - origin.getLng()) * mult));
        }

        return TimeFilterFastRequest
            .newBuilder()
            .setOneToManyRequest(oneToManyBuilder.build())
            .build()
            .toByteArray();
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String countryCode = oneToMany.getCountry().getValue();
        String transportation = oneToMany.getTransportation().getValue();
        String uri = baseUri + countryCode + "/time-filter/fast/" + transportation;
        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }

    private Either<TravelTimeError, TimeFilterFastProtoResponse> parseResponse(TimeFilterFastResponse response) {
        if(response.hasError()) {
            return Either.left(new ProtoError(response.getError().toString()));
        } else {
            return Either.right(new TimeFilterFastProtoResponse(response.getProperties().getTravelTimesList()));
        }
    }

    @Override
    public Either<TravelTimeError, TimeFilterFastProtoResponse> parseBytes(byte[] body) {
        return Try
            .of(() -> TimeFilterFastResponse.parseFrom(body))
            .toEither()
            .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_PROTO_ERROR + cause.getMessage()))
            .flatMap(this::parseResponse);
    }
}
