package com.traveltime.sdk.dto.requests.proto;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import lombok.*;

public interface Transportation {
    TransportationType getType();
    RequestsCommon.Transportation getProtoMessage();

    class Modes {
        public final static PublicTransport PUBLIC_TRANSPORT = PublicTransport.builder().build();
        public final static DrivingAndPublicTransport DRIVING_AND_PUBLIC_TRANSPORT = DrivingAndPublicTransport.builder().build();
        public final static Transportation WALKING_FERRY = new TransportationWithoutDetails(TransportationType.Types.WALKING_FERRY);
        public final static Transportation CYCLING_FERRY = new TransportationWithoutDetails(TransportationType.Types.CYCLING_FERRY);
        public final static Transportation DRIVING_FERRY = new TransportationWithoutDetails(TransportationType.Types.DRIVING_FERRY);
        public final static Transportation WALKING = new TransportationWithoutDetails(TransportationType.Types.WALKING);
        public final static Transportation CYCLING = new TransportationWithoutDetails(TransportationType.Types.CYCLING);
        public final static Transportation DRIVING = new TransportationWithoutDetails(TransportationType.Types.DRIVING);
    }

    @Builder
    @Getter
    class PublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.PUBLIC_TRANSPORT;

        /**
         * limits the possible duration of walking paths must be > 0 and <= 1800
         * walkingTimeToStation limit is of low precedence and will not override the global travel time limit
         */
        @Builder.Default
        @With
        private Integer walkingTimeToStation = 1800;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation
                .newBuilder()
                .setPublicTransport(
                    RequestsCommon.PublicTransportDetails
                        .newBuilder()
                        .setWalkingTimeToStation(this.walkingTimeToStation)
                )
                .setTypeValue(this.type.getCode())
                .build();
        }
    }

    @Builder
    @Getter
    class DrivingAndPublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.DRIVING_AND_PUBLIC_TRANSPORT;

        /**
         * limits the possible duration of walking paths must be > 0 and <= 1800
         * walkingTimeToStation limit is of low precedence and will not override the global travel time limit
         */
        @Builder.Default
        @With
        private Integer walkingTimeToStation = 1800;

        /**
         * limits the possible duration of driving paths must be > 0 and <= 1800
         * drivingTimeToStation limit is of low precedence and will not override the global travel time limit
         */
        @Builder.Default
        @With
        private Integer drivingTimeToStation = 1800;

        /**
         * constant penalty to apply to simulate the difficulty of finding a parking spot.
         * If parkingTime >= 0: apply the parking penalty when searching for possible paths.
         * parkingTime penalty cannot be greater than the global travel time limit
         */
        @Builder.Default
        @With
        private Integer parkingTime = 300;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation
                .newBuilder()
                .setDrivingAndPublicTransport(
                    RequestsCommon.DrivingAndPublicTransportDetails
                        .newBuilder()
                        .setWalkingTimeToStation(this.walkingTimeToStation)
                        .setDrivingTimeToStation(this.drivingTimeToStation)
                        .setParkingTime(this.parkingTime)
                )
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
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
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