package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.responses.TimeFilterFastResponseOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.TimeFilterFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.ProtoError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Data
@Builder
@AllArgsConstructor
@With
@EqualsAndHashCode(callSuper = true)
public class TimeFilterFastProtoRequest extends ProtoRequest<TimeFilterFastProtoResponse> {
    @NonNull
    Coordinates originCoordinate;

    @NonNull
    @Singular
    List<Coordinates> destinationCoordinates;

    @NonNull
    Transportation transportation;

    @NonNull
    Integer travelTime;

    @NonNull
    Country country;

    @NonNull
    RequestType requestType;

    boolean withDistance;

    /**
     * @param originCoordinate The coordinates of location we should start the search from.
     * @param destinationCoordinates The coordinates of locations we run the search to. If the class implementing this
     *     list does not implement the {@code RandomAccess} interface it will be internally converted into an
     *     {@code ArrayList}.
     * @param transportation Transportation mode.
     * @param travelTime Travel time limit.
     * @param country The country to run the search in.
     * @param withDistance Specifies if distance also should be returned.
     */
    public TimeFilterFastProtoRequest(
            @NonNull Coordinates originCoordinate,
            @NonNull List<Coordinates> destinationCoordinates,
            @NonNull Transportation transportation,
            @NonNull Integer travelTime,
            @NonNull Country country,
            @NonNull RequestType requestType,
            boolean withDistance) {

        this.originCoordinate = originCoordinate;
        if (destinationCoordinates instanceof RandomAccess) {
            this.destinationCoordinates = destinationCoordinates;
        } else {
            this.destinationCoordinates = new ArrayList<>(destinationCoordinates);
        }
        this.transportation = transportation;
        this.travelTime = travelTime;
        this.country = country;
        this.requestType = requestType;
        this.withDistance = withDistance;
    }

    String correlationId;

    private byte[] createByteArray() {
        Coordinates origin = this.getOriginCoordinate();

        RequestsCommon.Coords source = RequestsCommon.Coords.newBuilder()
                .setLat(this.originCoordinate.getLat().floatValue())
                .setLng(this.originCoordinate.getLng().floatValue())
                .build();

        RequestsCommon.Transportation transportation = this.transportation.getProtoMessage();

        if (requestType == RequestType.ONE_TO_MANY) {
            TimeFilterFastRequest.OneToMany.Builder oneToManyBuilder = TimeFilterFastRequest.OneToMany.newBuilder()
                    .setDepartureLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime);

            if (this.withDistance) {
                oneToManyBuilder.addProperties(TimeFilterFastRequest.Property.DISTANCES);
            }

            double mult = Math.pow(10, 5);
            for (Coordinates dest : this.destinationCoordinates) {
                oneToManyBuilder.addLocationDeltas((int) Math.round((dest.getLat() - origin.getLat()) * mult));
                oneToManyBuilder.addLocationDeltas((int) Math.round((dest.getLng() - origin.getLng()) * mult));
            }

            return TimeFilterFastRequest.newBuilder()
                    .setOneToManyRequest(oneToManyBuilder.build())
                    .build()
                    .toByteArray();
        } else {
            TimeFilterFastRequest.ManyToOne.Builder manyToOneBuilder = TimeFilterFastRequest.ManyToOne.newBuilder()
                    .setArrivalLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime);

            if (this.withDistance) {
                manyToOneBuilder.addProperties(TimeFilterFastRequest.Property.DISTANCES);
            }

            double mult = Math.pow(10, 5);
            for (Coordinates dest : this.destinationCoordinates) {
                manyToOneBuilder.addLocationDeltas((int) Math.round((dest.getLat() - origin.getLat()) * mult));
                manyToOneBuilder.addLocationDeltas((int) Math.round((dest.getLng() - origin.getLng()) * mult));
            }

            return TimeFilterFastRequest.newBuilder()
                    .setManyToOneRequest(manyToOneBuilder.build())
                    .build()
                    .toByteArray();
        }
    }

    @Override
    public List<Coordinates> getDestinationCoordinates() {
        return this.destinationCoordinates;
    }

    @Override
    public List<ProtoRequest<TimeFilterFastProtoResponse>> split(int batchSizeHint) {
        /* Naively splitting requests may lead to situations where the last request is very small and inefficient.
         * We adjust the batch sizes to never have a situation where a batch is smaller than (loadFactor * batchSizeHint).
         */

        float loadFactor = 0.1f;
        if (this.destinationCoordinates.size() <= batchSizeHint * (loadFactor + 1)) {
            return Collections.singletonList(this);
        } else {
            int batchCount = this.destinationCoordinates.size() / batchSizeHint;
            if (this.destinationCoordinates.size() % batchSizeHint > 0
                    && this.destinationCoordinates.size() % batchSizeHint < loadFactor * batchSizeHint) {
                batchCount -= 1;
            }
            int batchSize = (int) Math.ceil((float) this.destinationCoordinates.size() / batchCount);

            ArrayList<ProtoRequest<TimeFilterFastProtoResponse>> batchedDestinations = new ArrayList<>(batchCount);

            for (int offset = 0; offset < this.destinationCoordinates.size(); offset += batchSize) {
                List<Coordinates> batch = this.destinationCoordinates.subList(
                        offset, Math.min(offset + batchSize, this.destinationCoordinates.size()));
                batchedDestinations.add(this.withDestinationCoordinates(batch));
            }
            return batchedDestinations;
        }
    }

    @Override
    public TimeFilterFastProtoResponse merge(List<TimeFilterFastProtoResponse> responses) {
        List<Integer> times = responses.stream()
                .flatMap(resp -> resp.getTravelTimes().stream())
                .collect(Collectors.toList());

        List<Integer> distances =
                responses.stream().flatMap(resp -> resp.getDistances().stream()).collect(Collectors.toList());

        return new TimeFilterFastProtoResponse(times, distances);
    }

    @Override
    public Either<TravelTimeError, TimeFilterFastProtoResponse> parseBytes(byte[] body) {
        return getProtoResponse(body).flatMap(this::parseResponse);
    }

    private Either<TravelTimeError, TimeFilterFastProtoResponse> parseResponse(
            TimeFilterFastResponseOuterClass.TimeFilterFastResponse response) {
        // If `hasError == true`, it means you are hitting an old version of the service.
        // TODO: remove this `if` statement when all services use HTTP error codes
        if (response.hasError())
            return Either.left(new ProtoError(-1, response.getError().toString(), null, 200));
        else
            return Either.right(new TimeFilterFastProtoResponse(
                    response.getProperties().getTravelTimesList(),
                    response.getProperties().getDistancesList()));
    }

    @Override
    public String getCorrelationId() {
        if (correlationId == null) {
            return "no-x-correlation-id";
        } else {
            return correlationId;
        }
    }

    @Override
    public Either<TravelTimeError, Request> createRequest(HttpUrl baseUri, TravelTimeCredentials credentials) {
        String countryCode = this.country.getValue();
        String transportationType = this.transportation.getType().getValue();
        val uri = baseUri.newBuilder()
                .addPathSegments(countryCode)
                .addPathSegments("time-filter/fast")
                .addPathSegments(transportationType)
                .build();

        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }
}
