package com.traveltime.sdk.dto.requests;

import com.igeolise.traveltime.rabbitmq.requests.H3FastRequestOuterClass.H3FastRequest;
import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.responses.H3FastResponseOuterClass;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.CellProperty;
import com.traveltime.sdk.dto.requests.proto.Country;
import com.traveltime.sdk.dto.requests.proto.RequestType;
import com.traveltime.sdk.dto.requests.proto.Transportation;
import com.traveltime.sdk.dto.responses.H3FastProtoResponse;
import com.traveltime.sdk.dto.responses.errors.IOError;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import com.traveltime.sdk.dto.responses.errors.ValidationError;
import io.vavr.control.Either;
import io.vavr.control.Try;
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
public class H3FastProtoRequest extends ProtoRequest<H3FastProtoResponse> {
    private static final String IO_PROTO_ERROR = "Something went wrong when parsing proto response: ";

    @NonNull
    Coordinates originCoordinate;

    @NonNull
    Transportation transportation;

    @NonNull
    Integer travelTime;

    /**
     * H3 cell resolution. Supported values are 4 to 12, and the resolution caps the travel time a
     * search may use.
     *
     * @see <a href="https://docs.traveltime.com/api/reference/h3-fast#limits-of-resolution-and-traveltime">Limits of resolution and travel time</a>
     */
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
        boolean removeWater = this.removeWaterBodies == null || this.removeWaterBodies;

        if (requestType == RequestType.ONE_TO_MANY) {
            H3FastRequest.OneToMany oneToMany = H3FastRequest.OneToMany.newBuilder()
                    .setDepartureLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime)
                    .setResolution(this.resolution)
                    .setRemoveWaterBodies(removeWater)
                    .addAllProperties(CellProperty.toProtoOrAll(properties))
                    .build();

            return H3FastRequest.newBuilder()
                    .setOneToManyRequest(oneToMany)
                    .build()
                    .toByteArray();
        } else {
            H3FastRequest.ManyToOne manyToOne = H3FastRequest.ManyToOne.newBuilder()
                    .setArrivalLocation(source)
                    .setArrivalTimePeriod(RequestsCommon.TimePeriod.WEEKDAY_MORNING)
                    .setTransportation(transportation)
                    .setTravelTime(this.travelTime)
                    .setResolution(this.resolution)
                    .setRemoveWaterBodies(removeWater)
                    .addAllProperties(CellProperty.toProtoOrAll(properties))
                    .build();

            return H3FastRequest.newBuilder()
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
    public List<ProtoRequest<H3FastProtoResponse>> split(int batchSizeHint) {
        return Collections.singletonList(this);
    }

    @Override
    public H3FastProtoResponse merge(List<H3FastProtoResponse> responses) {
        return responses.get(0);
    }

    @Override
    public Either<TravelTimeError, H3FastProtoResponse> parseBytes(byte[] body) {
        return Try.of(() -> H3FastResponseOuterClass.H3FastResponse.parseFrom(body))
                .toEither()
                .<TravelTimeError>mapLeft(cause -> new IOError(cause, IO_PROTO_ERROR + cause.getMessage()))
                .map(response -> new H3FastProtoResponse(
                        response.getCells().getIdsList().stream()
                                .map(Long::toHexString)
                                .collect(Collectors.toList()),
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
        if (resolution < 4 || resolution > 12) {
            return Either.left(new ValidationError("resolution should be between 4 and 12"));
        }
        String countryCode = this.country.getValue();
        String transportationType = this.transportation.getType().getValue();
        val uri = baseUri.newBuilder()
                .addPathSegments(countryCode)
                .addPathSegments("h3/fast")
                .addPathSegments(transportationType)
                .build();

        return Either.right(createProtobufRequest(credentials, uri, createByteArray()));
    }
}
