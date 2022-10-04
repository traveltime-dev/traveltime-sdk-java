package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.OneToMany;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.*;
import okhttp3.Request;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@With
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastProtoRequest extends ProtoRequest {
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

    @Override
    public Either<TravelTimeError, Request> createRequest(URI baseUri, TravelTimeCredentials credentials) {
        String countryCode = oneToMany.getCountry().getValue();
        String transportation = oneToMany.getTransportation().getValue();
        String uri = baseUri + countryCode + "/time-filter/fast/" + transportation;
        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }
}
