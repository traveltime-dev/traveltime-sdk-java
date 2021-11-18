package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass.TimeFilterFastResponse;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterProtoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.ProtoError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.Request;

@Getter
@Builder
@AllArgsConstructor
public class TimeFilterProtoRequest extends ProtoRequest<TimeFilterProtoResponse> {
    private static final String BASE_URI = "https://proto.api.traveltimeapp.com/api/v2/";
    @NonNull
    OneToMany oneToMany;

    private byte[] createByteArray() {
        Coordinates origin = oneToMany.getOriginCoordinate();

        RequestsCommon.Coords departure = RequestsCommon
            .Coords
            .newBuilder()
            .setLat((float)origin.getLat())
            .setLng((float)origin.getLng())
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
    public Either<TravelTimeError, Request> createRequest(String username, String password) {
        String countryCode = oneToMany.getCountry().getValue();
        String transportation = oneToMany.getTransportation().getValue();
        String uri = BASE_URI + countryCode + "/time-filter/fast/" + transportation;
        return Either.right(createProtobufRequest(uri, username, password, createByteArray()));
    }

    private Either<TravelTimeError, TimeFilterProtoResponse> parseResponse(TimeFilterFastResponse response) {
        if(response.hasError()) {
            return Either.left(new ProtoError(response.getError().toString()));
        } else {
            return Either.right(new TimeFilterProtoResponse(response.getProperties().getTravelTimesList()));
        }
    }

    @Override
    public Either<TravelTimeError, TimeFilterProtoResponse> parseBytes(byte[] body) {
        return Try
            .of(() -> TimeFilterFastResponse.parseFrom(body))
            .toEither()
            .<TravelTimeError>mapLeft(IOError::new)
            .flatMap(this::parseResponse);
    }
}
