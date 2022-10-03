package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.protodistance.Country;
import com.traveltime.sdk.dto.requests.protodistance.Transportation;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.NonNull;
import okhttp3.Request;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class TimeFilterProtoDistanceRequest extends ProtoRequest {
    @NonNull
    Coordinates originCoordinate;
    @NonNull
    List<Coordinates> destinationCoordinates;
    @NonNull
    Country country;
    @NonNull
    Transportation transportation;
    @NonNull
    Integer travelTime;

    private byte[] createByteArray() {

        RequestsCommon.Coords departure = RequestsCommon
                .Coords
                .newBuilder()
                .setLat(originCoordinate.getLat().floatValue())
                .setLng(originCoordinate.getLng().floatValue())
                .build();

        RequestsCommon.Transportation transportationType = RequestsCommon
                .Transportation
                .newBuilder()
                .setTypeValue(transportation.getCode())
                .build();

        TimeFilterFastRequestOuterClass.TimeFilterFastRequest.OneToMany.Builder oneToManyBuilder = TimeFilterFastRequestOuterClass.TimeFilterFastRequest
                .OneToMany
                .newBuilder()
                .setDepartureLocation(departure)
                .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                .setTransportation(transportationType)
                .setTravelTime(travelTime);

        double mult = Math.pow(10, 5);
        for(Coordinates dest : destinationCoordinates) {
            oneToManyBuilder.addLocationDeltas((int)Math.round((dest.getLat() - originCoordinate.getLat()) * mult));
            oneToManyBuilder.addLocationDeltas((int)Math.round((dest.getLng() - originCoordinate.getLng()) * mult));
        }

        return TimeFilterFastRequestOuterClass.TimeFilterFastRequest
                .newBuilder()
                .setOneToManyRequest(oneToManyBuilder.build())
                .build()
                .toByteArray();
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String protoDistanceUri = "https://proto-with-distance.api.traveltimeapp.com/api/v2/";
        String uri = protoDistanceUri + country.getValue() + "/time-filter/fast/" + transportation.getValue();
        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }

    /**
     * @param originCoordinate       The coordinates of location we should start the search from.
     * @param destinationCoordinates The coordinates of locations we run the search to. If the class implementing this list
     *                               does not implement the {@code RandomAccess} interface it will be internally converted into an {@code ArrayList}.
     * @param travelTime             Travel time limit.
     */
    public TimeFilterProtoDistanceRequest(
        @NonNull Coordinates originCoordinate,
        @NonNull List<Coordinates> destinationCoordinates,
        @NonNull Integer travelTime,
        @NonNull Transportation transportation,
        @NonNull Country country
    ) {
        this.originCoordinate = originCoordinate;
        if (destinationCoordinates instanceof RandomAccess) {
            this.destinationCoordinates = destinationCoordinates;
        } else {
            this.destinationCoordinates = new ArrayList<>(destinationCoordinates);
        }
        this.travelTime = travelTime;
        this.transportation = transportation;
        this.country = country;
    }
}
