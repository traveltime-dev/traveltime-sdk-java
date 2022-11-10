package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.protodistance.Country;
import com.traveltime.sdk.dto.requests.protodistance.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoDistanceResponse;
import com.traveltime.sdk.dto.responses.errors.ProtoError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
import java.util.stream.Collectors;

@Value
@Builder
@AllArgsConstructor
@With
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastProtoDistanceRequest extends ProtoRequest<TimeFilterFastProtoDistanceResponse> {
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
                .addProperties(TimeFilterFastRequestOuterClass.TimeFilterFastRequest.Property.DISTANCES)
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
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        val uri = baseUri.newBuilder().addPathSegments(country.getValue()).addPathSegments("/time-filter/fast/").addPathSegments(transportation.getValue()).build();
        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }

    public Either<TravelTimeError, TimeFilterFastProtoDistanceResponse> parseBytes(byte[] body) {
        return getProtoResponse(body).flatMap(this::parseResponse);
    }

    private Either<TravelTimeError, TimeFilterFastProtoDistanceResponse> parseResponse(
        TimeFilterFastResponseOuterClass.TimeFilterFastResponse response
    ) {
        if(response.hasError())
            return Either.left(new ProtoError(response.getError().toString()));
        else
            return Either.right(
                new TimeFilterFastProtoDistanceResponse(
                    response.getProperties().getTravelTimesList(),
                    response.getProperties().getDistancesList()
                )
            );
    }

    @Override
    public List<Coordinates> getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public List<ProtoRequest<TimeFilterFastProtoDistanceResponse>> split(int batchSizeHint) {
        /* Naively splitting requests may lead to situations where the last request is very small and inefficient.
         * We adjust the batch sizes to never have a situation where a batch is smaller than (loadFactor * batchSizeHint).
         */
        float loadFactor = 0.1f;
        if (destinationCoordinates.size() <= batchSizeHint * (loadFactor + 1)) {
            return Collections.singletonList(this);
        } else {
            int batchCount = destinationCoordinates.size() / batchSizeHint;
            if (destinationCoordinates.size() % batchSizeHint > 0 &&
                    destinationCoordinates.size() % batchSizeHint < loadFactor * batchSizeHint) {
                batchCount -= 1;
            }
            int batchSize = (int) Math.ceil((float) destinationCoordinates.size() / batchCount);

            ArrayList<ProtoRequest<TimeFilterFastProtoDistanceResponse>> batchedDestinations = new ArrayList<>(batchCount);

            for (int offset = 0; offset < destinationCoordinates.size(); offset += batchSize) {
                List<Coordinates> batch = destinationCoordinates.subList(offset, Math.min(offset + batchSize, destinationCoordinates.size()));
                batchedDestinations.add(new TimeFilterFastProtoDistanceRequest(originCoordinate, batch, travelTime, transportation, country));
            }
            return batchedDestinations;
        }
    }

    @Override
    public TimeFilterFastProtoDistanceResponse merge(List<TimeFilterFastProtoDistanceResponse> responses) {
        List<Integer> times = responses
            .stream()
            .flatMap(resp -> resp.getTravelTimes().stream())
            .collect(Collectors.toList());

        List<Integer> distances = responses
            .stream()
            .flatMap(resp -> resp.getDistances().stream())
            .collect(Collectors.toList());
        return new TimeFilterFastProtoDistanceResponse(times, distances);
    }

    /**
     * @param originCoordinate       The coordinates of location we should start the search from.
     * @param destinationCoordinates The coordinates of locations we run the search to. If the class implementing this list
     *                               does not implement the {@code RandomAccess} interface it will be internally converted into an {@code ArrayList}.
     * @param travelTime             Travel time limit.
     */
    public TimeFilterFastProtoDistanceRequest(
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
