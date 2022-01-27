package com.traveltime.sdk.dto.requests.proto;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.requests.proto.Country;

import com.igeolise.traveltime.rabbitmq.requests.TimeFilterFastRequestOuterClass.TimeFilterFastRequest;
import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon;
import com.igeolise.traveltime.rabbitmq.requests.RequestsCommon.Coords;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class OneToMany {
    private TimeFilterFastRequest.OneToMany underlying;
    private Country country;

    private static final double mult = Math.pow(10, 5);

    public OneToMany(Coordinates origin, List<Coordinates> destinations, Transportation transportation, int travelTime, Country country) {
        Builder builder = new Builder(origin);

        for (Coordinates dest : destinations) {
            builder.addDestination(dest);
        }

        builder.setTransportation(transportation);
        builder.setTravelTime(travelTime);

        this.underlying = builder.underlying.build();

        this.country = country;
    }

    public OneToMany(Coordinates origin, List<Coordinates> destinations, Transportation transportation, Integer travelTime, Country country) {
        this(origin, destinations, transportation, travelTime.intValue(), country);
    }

    public TimeFilterFastRequest.OneToMany getUnderlying() {
        return underlying;
    }

    public Coordinates getOriginCoordinate() {
        return coordinatesOf(underlying.getDepartureLocation());
    }

    public List<Coordinates> getDestinationCoordinates() {
        List<Coordinates> coords = new ArrayList<Coordinates>();

        Iterator<Integer> iterator = underlying.getLocationDeltasList().iterator();

        Coordinates origin = getOriginCoordinate();

        while (iterator.hasNext()) {
            int d1 = iterator.next();
            int d2 = iterator.next();

            double lat = (d1 / mult) + origin.getLat();
            double lon = (d2 / mult) + origin.getLng();

            coords.add(new Coordinates(Double.valueOf(lat), Double.valueOf(lon)));
        }

        return coords;
    }

    public int getTravelTime() {
        return underlying.getTravelTime();
    }

    public Transportation getTransportation() {
        return Transportation.ofInt(this.underlying.getTransportation().getType().getNumber());
    }

    public Country getCountry() {
        return country;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof OneToMany) {
            OneToMany that = (OneToMany) obj;
            return this.underlying.equals(that.underlying);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return underlying.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("OneToMany(originCoordinate=");
        sb.append(getOriginCoordinate());
        sb.append(", destinationCoordinates=");
        sb.append(getDestinationCoordinates());
        sb.append(" , transportation=");
        sb.append(getTransportation());
        sb.append(", travelTime=");
        sb.append(getTravelTime());
        sb.append(", country=");
        sb.append(getCountry());
        sb.append(")");

        return sb.toString();
    }

    public static Builder builder(Coordinates origin) {
        return new Builder(origin);
    }

    public static class Builder {
        private TimeFilterFastRequest.OneToMany.Builder underlying;
        private Coordinates origin;
        private Country country;

        public Builder(Coordinates origin) {
            underlying = TimeFilterFastRequest.OneToMany.newBuilder();
            this.underlying.setDepartureLocation(coordsOf(origin));
            this.origin = origin;
        }
        
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            } else if (obj instanceof OneToMany) {
                Builder that = (OneToMany.Builder) obj;
                return this.underlying.equals(that.underlying);
            } else {
                return false;
            }
        }

        public Coordinates getOriginCoordinate() {
            return coordinatesOf(underlying.getDepartureLocation());
        }

        public List<Coordinates> getDestinationCoordinates() {
            List<Coordinates> coords = new ArrayList<Coordinates>();

            Iterator<Integer> iterator = underlying.getLocationDeltasList().iterator();

            Coordinates origin = getOriginCoordinate();

            while (iterator.hasNext()) {
                int d1 = iterator.next();
                int d2 = iterator.next();

                double lat = (d1 / mult) + origin.getLat();
                double lon = (d2 / mult) + origin.getLng();

                coords.add(new Coordinates(Double.valueOf(lat), Double.valueOf(lon)));
            }

            return coords;
        }

        public Builder addDestination(Coordinates dest) {
             underlying.addLocationDeltas((int)Math.round((dest.getLat() - origin.getLat()) * mult));
             underlying.addLocationDeltas((int)Math.round((dest.getLng() - origin.getLng()) * mult));
             return this;
        }

        public int getTravelTime() {
            return underlying.getTravelTime();
        }

        public Builder setTravelTime(int travelTime) {
            underlying.setTravelTime(travelTime);
            return this;
        }

        public Builder setTransportation(Transportation transportation) {
            this.underlying.setTransportation(protoTransportationOf(transportation));
            return this;
        }

        public Transportation getTransportation() {
            return Transportation.ofInt(this.underlying.getTransportation().getType().getNumber());
        }

        public Country getCountry() {
            return country;
        }

        public int hashCode() {
            return underlying.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("OneToMany(originCoordinate=");
            sb.append(getOriginCoordinate());
            sb.append(", destinationCoordinates=");
            sb.append(getDestinationCoordinates());
            sb.append(" , transportation=");
            sb.append(getTransportation());
            sb.append(", travelTime=");
            sb.append(getTravelTime());
            sb.append(", country=");
            sb.append(getCountry());
            sb.append(")");

            return sb.toString();
        }
    }

    static Coordinates coordinatesOf(Coords coords) {
        return new Coordinates(
            Double.valueOf(coords.getLat()),
            Double.valueOf(coords.getLng()));
    }

    static RequestsCommon.Transportation protoTransportationOf(Transportation t) {
        return RequestsCommon.Transportation.newBuilder().setTypeValue(t.getCode()).build();
    }

    static Coords coordsOf(Coordinates coordinates) {
        return Coords.newBuilder().setLat(coordinates.getLat().floatValue()).setLng(coordinates.getLng().floatValue()).build();
    }
}
