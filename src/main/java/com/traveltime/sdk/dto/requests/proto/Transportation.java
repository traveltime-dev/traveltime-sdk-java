package com.traveltime.sdk.dto.requests.proto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public interface Transportation {
    TransportationType getType();
    TransportationDetails getDetails();

    @Getter
    @AllArgsConstructor
    enum Defaults implements Transportation {
        PUBLIC_TRANSPORT(PublicTransport.builder().build()),
        DRIVING_AND_PUBLIC_TRANSPORT(DrivingAndPublicTransport.builder().build()),
        WALKING_FERRY(new TransportationWithoutDetails(TransportationType.Modes.WALKING_FERRY, TransportationDetails.NoDetails)),
        CYCLING_FERRY(new TransportationWithoutDetails(TransportationType.Modes.CYCLING_FERRY, TransportationDetails.NoDetails)),
        DRIVING_FERRY(new TransportationWithoutDetails(TransportationType.Modes.DRIVING_FERRY, TransportationDetails.NoDetails)),
        WALKING(new TransportationWithoutDetails(TransportationType.Modes.WALKING, TransportationDetails.NoDetails)),
        CYCLING(new TransportationWithoutDetails(TransportationType.Modes.CYCLING, TransportationDetails.NoDetails)),
        DRIVING(new TransportationWithoutDetails(TransportationType.Modes.DRIVING, TransportationDetails.NoDetails)),;

        final Transportation value;

        @Override
        public TransportationType getType() {
            return value.getType();
        }

        @Override
        public TransportationDetails getDetails() {
            return value.getDetails();
        }

        @Getter
        @AllArgsConstructor
        private static class TransportationWithoutDetails implements Transportation {
            public final TransportationType type;
            public final TransportationDetails details;
        }
    }

    @Builder
    @Getter
    class PublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Modes.PUBLIC_TRANSPORT;

        @Builder.Default
        public TransportationDetails.PublicTransportDetails details =
            new TransportationDetails.PublicTransportDetails(1800);
    }

    @Builder
    @Getter
    class DrivingAndPublicTransport implements Transportation {
        public final TransportationType type = TransportationType.Modes.DRIVING_AND_PUBLIC_TRANSPORT;

        @Builder.Default
        public final TransportationDetails details = new TransportationDetails.DrivingAndPublicTransportDetails(
            1800, 1800, 300
        );
    }

    interface TransportationType {
        String getValue();
        Integer getCode();

        @Getter
        @AllArgsConstructor
        enum Modes implements TransportationType {
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

        class NoDetails implements TransportationDetails { }
        TransportationDetails NoDetails = new NoDetails();
    }
}