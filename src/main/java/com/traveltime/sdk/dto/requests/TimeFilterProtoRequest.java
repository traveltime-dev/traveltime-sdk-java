package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterProtoResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.Request;

@Getter
@Builder
@AllArgsConstructor
public class TimeFilterProtoRequest extends TravelTimeRequest<TimeFilterProtoResponse> {
    private final String BASE_URI = "https://proto.api.traveltimeapp.com/api/v2/nl/time-filter/fast/";
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

        TimeFilterFastRequestOuterClass.TimeFilterFastRequest.OneToMany.Builder oneToManyBuilder = TimeFilterFastRequestOuterClass.TimeFilterFastRequest
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

        return TimeFilterFastRequestOuterClass.TimeFilterFastRequest
            .newBuilder()
            .setOneToManyRequest(oneToManyBuilder.build())
            .build()
            .toByteArray();
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(String username, String password) {
        String uri = BASE_URI + oneToMany.getTransportation().getValue();
        return Either.right(createProtobufRequest(uri, username, password, createByteArray()));
    }

    @Override
    public Class<TimeFilterProtoResponse> responseType() {
        return TimeFilterProtoResponse.class;
    }


    @Override
    public Boolean isProto() {
        return true;
    }
}
