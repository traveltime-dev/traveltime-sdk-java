package com.traveltime.sdk.dto.requests.proto;

import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface Transportation {
    TransportationType getType();

    RequestsCommon.Transportation getProtoMessage();

    class Modes {
        public final static Transportation PUBLIC_TRANSPORT = PublicTransport.builder().build();
        public final static Transportation DRIVING_AND_PUBLIC_TRANSPORT = DrivingAndPublicTransport.builder().build();
        public final static Transportation WALKING_FERRY = new WalkingFerry();
        public final static Transportation CYCLING_FERRY = new CyclingFerry();
        public final static Transportation DRIVING_FERRY = new DrivingFerry();
        public final static Transportation WALKING = new Walking();
        public final static Transportation CYCLING = new Cycling();
        public final static Transportation DRIVING = new Driving();
    }

    @Builder
    @Getter
    class PublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.PUBLIC_TRANSPORT;

        @Builder.Default
        public TransportationDetails.PublicTransportDetails details =
            new TransportationDetails.PublicTransportDetails(1800);

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation
                .newBuilder()
                .setPublicTransport(
                    RequestsCommon.PublicTransportDetails
                        .newBuilder()
                        .setWalkingTimeToStation(this.details.walkingTimeToStation)
                )
                .setTypeValue(this.type.getCode())
                .build();
        }
    }

    @Builder
    @Getter
    class DrivingAndPublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Types.DRIVING_AND_PUBLIC_TRANSPORT;

        @Builder.Default
        public final TransportationDetails.DrivingAndPublicTransportDetails details = new TransportationDetails.DrivingAndPublicTransportDetails(
            1800, 1800, 300
        );

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation
                .newBuilder()
                .setDrivingAndPublicTransport(
                    RequestsCommon.DrivingAndPublicTransportDetails
                        .newBuilder()
                        .setWalkingTimeToStation(this.details.walkingTimeToStation)
                        .setDrivingTimeToStation(this.details.drivingTimeToStation)
                        .setParkingTime(this.details.parkingTime)
                )
                .setTypeValue(this.type.getCode())
                .build();
        }
    }

    @Getter
    class WalkingFerry implements Transportation {
        public final TransportationType type = TransportationType.Types.WALKING_FERRY;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
        }
    }

    @Getter
    class CyclingFerry implements Transportation {
        public final TransportationType type = TransportationType.Types.CYCLING_FERRY;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
        }
    }

    @Getter
    class DrivingFerry implements Transportation {
        public final TransportationType type = TransportationType.Types.DRIVING_FERRY;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
        }
    }

    @Getter
    class Walking implements Transportation {
        public final TransportationType type = TransportationType.Types.WALKING;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
        }
    }

    @Getter
    class Cycling implements Transportation {
        public final TransportationType type = TransportationType.Types.CYCLING;

        @Override
        public RequestsCommon.Transportation getProtoMessage() {
            return RequestsCommon.Transportation.newBuilder().setTypeValue(this.type.getCode()).build();
        }
    }

    @Getter
    class Driving implements Transportation {
        public final TransportationType type = TransportationType.Types.DRIVING;

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
            PUBLIC_TRANSPORT("pt", 0),
            DRIVING_AND_PUBLIC_TRANSPORT("pt", 2),
            WALKING_FERRY("walking+ferry", 7),
            CYCLING_FERRY("cycling+ferry", 6),
            DRIVING_FERRY("driving+ferry", 3),
            WALKING("walking", 4),
            CYCLING("cycling", 5),
            DRIVING("driving", 1);

            private final String value;
            private final Integer code;
        }

        @Getter
        @AllArgsConstructor
        class Custom implements TransportationType {
            private final String value;
            private final Integer code;
        }
    }

    interface TransportationDetails {

        @Getter
        @AllArgsConstructor
        class PublicTransportDetails implements TransportationDetails {
            /**
             * limits the possible duration of walking paths must be > 0 and <= 1800
             * walkingTimeToStation limit is of low precedence and will not override the global travel time limit
             */
            private final Integer walkingTimeToStation;
        }

        @Getter
        @AllArgsConstructor
        class DrivingAndPublicTransportDetails implements TransportationDetails {
            /**
             * limits the possible duration of walking paths must be > 0 and <= 1800
             * walkingTimeToStation limit is of low precedence and will not override the global travel time limit
             */
            private final Integer walkingTimeToStation;

            /**
             * limits the possible duration of driving paths must be > 0 and <= 1800
             * drivingTimeToStation limit is of low precedence and will not override the global travel time limit
             */
            private final Integer drivingTimeToStation;

            /**
             * constant penalty to apply to simulate the difficulty of finding a parking spot.
             * If parkingTime >= 0: apply the parking penalty when searching for possible paths.
             * parkingTime penalty cannot be greater than the global travel time limit
             */
            private final Integer parkingTime;
        }
    }
}