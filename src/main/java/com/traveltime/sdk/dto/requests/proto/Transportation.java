package com.traveltime.sdk.dto.requests.proto;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import lombok.*;

public interface Transportation {
    TransportationType getType();

    RequestsCommon.Transportation getProtoMessage();

    class Modes {
        public static final PublicTransport PUBLIC_TRANSPORT =
                PublicTransport.builder().build();
        public static final DrivingAndPublicTransport DRIVING_AND_PUBLIC_TRANSPORT =
                DrivingAndPublicTransport.builder().build();
        public static final Transportation WALKING_FERRY =
                new TransportationWithoutDetails(TransportationType.Types.WALKING_FERRY);
        public static final Transportation CYCLING_FERRY =
                new TransportationWithoutDetails(TransportationType.Types.CYCLING_FERRY);
        public static final Transportation DRIVING_FERRY =
                new TransportationWithoutDetails(TransportationType.Types.DRIVING_FERRY);
        public static final Transportation WALKING = new TransportationWithoutDetails(TransportationType.Types.WALKING);
        public static final Transportation CYCLING = new TransportationWithoutDetails(TransportationType.Types.CYCLING);
        public static final Transportation DRIVING = new TransportationWithoutDetails(TransportationType.Types.DRIVING);
    }

    @Builder
    @Getter
    class PublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.PUBLIC_TRANSPORT;

        /**
         * limits the possible duration of walking paths must be > 0 and <= 1800 walkingTimeToStation limit is of low
         * precedence and will not override the global travel time limit If not set, server side decides what
         * `walkingTimeToStation` value should be which is a reasonable value within the [1, 1800] (inclusive)
         */
        @Builder.Default
        @With
        private Integer walkingTimeToStation = null;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            RequestsCommon.PublicTransportDetails.Builder detailsBuilder =
                    RequestsCommon.PublicTransportDetails.newBuilder();

            if (this.walkingTimeToStation != null) {
                RequestsCommon.OptionalPositiveUInt32 walkingTime = RequestsCommon.OptionalPositiveUInt32.newBuilder()
                        .setValue(this.walkingTimeToStation)
                        .build();
                detailsBuilder.setWalkingTimeToStation(walkingTime);
            }

            return RequestsCommon.Transportation.newBuilder()
                    .setPublicTransport(detailsBuilder.build())
                    .setTypeValue(this.type.getCode())
                    .build();
        }
    }

    @Builder
    @Getter
    class DrivingAndPublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.DRIVING_AND_PUBLIC_TRANSPORT;

        /**
         * limits the possible duration of walking paths must be > 0 and <= 1800 walkingTimeToStation limit is of low
         * precedence and will not override the global travel time limit If not set, server side decides what
         * `walkingTimeToStation` value should be which is a reasonable value within the [1, 1800] (inclusive)
         */
        @Builder.Default
        @With
        private Integer walkingTimeToStation = null;

        /**
         * limits the possible duration of driving paths must be > 0 and <= 1800 drivingTimeToStation limit is of low
         * precedence and will not override the global travel time limit If not set, server side decides what
         * `drivingTimeToStation` value should be which is a reasonable value within the [1, 1800] (inclusive)
         */
        @Builder.Default
        @With
        private Integer drivingTimeToStation = null;

        /**
         * constant penalty to apply to simulate the difficulty of finding a parking spot. If parkingTime >= 0: apply
         * the parking penalty when searching for possible paths. parkingTime penalty cannot be greater than the global
         * travel time limit If not set, server side decides what `parkingTime` value should be
         */
        @Builder.Default
        @With
        private Integer parkingTime = null;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            RequestsCommon.DrivingAndPublicTransportDetails.Builder detailsBuilder =
                    RequestsCommon.DrivingAndPublicTransportDetails.newBuilder();

            if (this.walkingTimeToStation != null) {
                RequestsCommon.OptionalPositiveUInt32 walkingTime = RequestsCommon.OptionalPositiveUInt32.newBuilder()
                        .setValue(this.walkingTimeToStation)
                        .build();
                detailsBuilder.setWalkingTimeToStation(walkingTime);
            }

            if (this.drivingTimeToStation != null) {
                RequestsCommon.OptionalPositiveUInt32 drivingTime = RequestsCommon.OptionalPositiveUInt32.newBuilder()
                        .setValue(this.drivingTimeToStation)
                        .build();
                detailsBuilder.setDrivingTimeToStation(drivingTime);
            }

            if (this.parkingTime != null) {
                RequestsCommon.OptionalNonNegativeUInt32 parkingTime =
                        RequestsCommon.OptionalNonNegativeUInt32.newBuilder()
                                .setValue(this.parkingTime)
                                .build();
                detailsBuilder.setParkingTime(parkingTime);
            }

            return RequestsCommon.Transportation.newBuilder()
                    .setDrivingAndPublicTransport(detailsBuilder.build())
                    .setTypeValue(this.type.getCode())
                    .build();
        }
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    class TransportationWithoutDetails implements Transportation {
        TransportationType type;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder()
                    .setTypeValue(this.type.getCode())
                    .build();
        }
    }

    interface TransportationType {
        String getValue();

        Integer getCode();

        @Getter
        @AllArgsConstructor
        enum Types implements TransportationType {
            PUBLIC_TRANSPORT("pt", RequestsCommon.TransportationType.PUBLIC_TRANSPORT_VALUE),
            DRIVING_AND_PUBLIC_TRANSPORT("pt", RequestsCommon.TransportationType.DRIVING_AND_PUBLIC_TRANSPORT_VALUE),
            WALKING_FERRY("walking+ferry", RequestsCommon.TransportationType.WALKING_AND_FERRY_VALUE),
            CYCLING_FERRY("cycling+ferry", RequestsCommon.TransportationType.CYCLING_AND_FERRY_VALUE),
            DRIVING_FERRY("driving+ferry", RequestsCommon.TransportationType.DRIVING_AND_FERRY_VALUE),
            WALKING("walking", RequestsCommon.TransportationType.WALKING_VALUE),
            CYCLING("cycling", RequestsCommon.TransportationType.CYCLING_VALUE),
            DRIVING("driving", RequestsCommon.TransportationType.DRIVING_VALUE);

            private final String value;
            private final Integer code;
        }
    }
}
