package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.GeohashFastRequestOuterClass.GeohashFastRequest;
import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.responses.GeohashFastResponseOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.CellProperty;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.GeohashFastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.*;
import lombok.*;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Data
@Builder
@AllArgsConstructor
@With
@EqualsAndHashCode(callSuper = true)
public class GeohashFastProtoRequest extends ProtoRequest<GeohashFastProtoResponse> {
    private static final String IO_PROTO_ERROR = "Something went wrong when parsing proto response: ";

    @NonNull
    Coordinates originCoordinate;

    @NonNull
    Transportation transportation;

    @NonNull
    Integer travelTime;

    @NonNull
    Integer resolution;

    @NonNull
    Country country;

    @NonNull
    RequestType requestType;

    String correlationId;

    /**
     * Travel time statistics to return. All three are returned when left unset; an empty list
     * returns cell ids with no travel times.
     */
    List<CellProperty> properties;

    /**
     * When true (the API default), the returned cells will not cover large nearby water bodies.
     * Set to false to allow cells over water bodies like large lakes, wide rivers, and seas.
     */
    @Builder.Default
    Boolean removeWaterBodies = true;

    private byte[] createByteArray() {
        RequestsCommon.Coords source = RequestsCommon.Coords.newBuilder()
                .setLat(this.originCoordinate.getLat().floatValue())
                .setLng(this.originCoordinate.getLng().floatValue())
                .build();

        RequestsCommon.Transportation transportation = this.transportation.getProtoMessage();

        if (requestType == RequestType.ONE_TO_MANY) {
            GeohashFastRequest.OneToMany oneToMany = GeohashFastRequest.OneToMany.newBuilder()
                    .setDepartureLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime)
                    .setResolution(this.resolution)
                    .setRemoveWaterBodies(this.removeWaterBodies == null || this.removeWaterBodies)
                    .addAllProperties(CellProperty.toProtoOrAll(properties))
                    .build();

            return GeohashFastRequest.newBuilder()
                    .setOneToManyRequest(oneToMany)
                    .build()
                    .toByteArray();
        } else {
            GeohashFastRequest.ManyToOne manyToOne = GeohashFastRequest.ManyToOne.newBuilder()
                    .setArrivalLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime)
                    .setResolution(this.resolution)
                    .setRemoveWaterBodies(this.removeWaterBodies == null || this.removeWaterBodies)
                    .addAllProperties(CellProperty.toProtoOrAll(properties))
                    .build();

            return GeohashFastRequest.newBuilder()
                    .setManyToOneRequest(manyToOne)
                    .build()
                    .toByteArray();
        }
    }

    @Override
    public List<Coordinates> getDestinationCoordinates() {
        return Collections.emptyList();
    }

    @Override
    public List<ProtoRequest<GeohashFastProtoResponse>> split(int batchSizeHint) {
        return Collections.singletonList(this);
    }

    @Override
    public GeohashFastProtoResponse merge(List<GeohashFastProtoResponse> responses) {
        return responses.get(0);
    }

    @Override
    public Either<TravelTimeError, GeohashFastProtoResponse> parseBytes(byte[] body) {
        return Try.of(() -> GeohashFastResponseOuterClass.GeohashFastResponse.parseFrom(body))
                .toEither()
                .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_PROTO_ERROR + cause.getMessage()))
                .map(response -> new GeohashFastProtoResponse(
                        response.getCells().getIdsList(),
                        response.getCells().getMinTravelTimesList(),
                        response.getCells().getMaxTravelTimesList(),
                        response.getCells().getMeanTravelTimesList()));
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
        if (resolution < 4 || resolution > 7) {
            return Either.left(new ValidationError("resolution should be between 4 and 7"));
        }
        String countryCode = this.country.getValue();
        String transportationType = this.transportation.getType().getValue();
        val uri = baseUri.newBuilder()
                .addPathSegments(countryCode)
                .addPathSegments("geohash/fast")
                .addPathSegments(transportationType)
                .build();

        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }
}
