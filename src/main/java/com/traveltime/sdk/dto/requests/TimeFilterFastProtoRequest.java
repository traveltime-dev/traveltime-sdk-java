package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
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
import java.util.stream.Collectors;

@Value
@Builder
@AllArgsConstructor
@With
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastProtoRequest extends ProtoRequest<TimeFilterFastProtoResponse> {
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
    public List<Coordinates> getDestinationCoordinates() {
        return oneToMany.getDestinationCoordinates();
    }

    @Override
    public List<ProtoRequest<TimeFilterFastProtoResponse>> split(int batchSizeHint) {
        /* Naively splitting requests may lead to situations where the last request is very small and inefficient.
         * We adjust the batch sizes to never have a situation where a batch is smaller than (loadFactor * batchSizeHint).
         */

        float loadFactor = 0.1f;
        List<Coordinates> originalDestinations = oneToMany.getDestinationCoordinates();
        if (originalDestinations.size() <= batchSizeHint * (loadFactor + 1)) {
            return Collections.singletonList(this);
        } else {
            int batchCount = originalDestinations.size() / batchSizeHint;
            if (originalDestinations.size() % batchSizeHint > 0 &&
                originalDestinations.size() % batchSizeHint < loadFactor * batchSizeHint) {
                batchCount -= 1;
            }
            int batchSize = (int) Math.ceil((float) originalDestinations.size() / batchCount);

            ArrayList<ProtoRequest<TimeFilterFastProtoResponse>> batchedDestinations = new ArrayList<>(batchCount);

            for (int offset = 0; offset < originalDestinations.size(); offset += batchSize) {
                List<Coordinates> batch = originalDestinations.subList(offset, Math.min(offset + batchSize, originalDestinations.size()));
                batchedDestinations.add(
                    this.withOneToMany(
                        oneToMany.withDestinationCoordinates(batch)
                    )
                );
            }
            return batchedDestinations;
        }
    }

    @Override
    public TimeFilterFastProtoResponse merge(List<TimeFilterFastProtoResponse> responses) {
        List<Integer> times = responses
            .stream()
            .flatMap(resp -> resp.getTravelTimes().stream())
            .collect(Collectors.toList());
        return new TimeFilterFastProtoResponse(times);
    }

    @Override
    public Either<TravelTimeError, TimeFilterFastProtoResponse> parseBytes(byte[] body) {
        return getProtoResponse(body).flatMap(this::parseResponse);
    }

    private Either<TravelTimeError, TimeFilterFastProtoResponse> parseResponse(
        TimeFilterFastResponseOuterClass.TimeFilterFastResponse response
    ) {
        if(response.hasError())
            return Either.left(new ProtoError(response.getError().toString()));
        else
            return Either.right(new TimeFilterFastProtoResponse(response.getProperties().getTravelTimesList()));
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        String countryCode = oneToMany.getCountry().getValue();
        String transportation = oneToMany.getTransportation().getValue();
        val uri = baseUri.newBuilder()
                .addPathSegments(countryCode)
                .addPathSegments("time-filter/fast")
                .addPathSegments(transportation)
                .build();

        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }
}
