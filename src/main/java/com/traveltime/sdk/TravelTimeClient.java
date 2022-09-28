package com.traveltime.sdk;

import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.FullRange;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Transportation;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.TimeMapRequest;
import com.traveltime.sdk.dto.requests.time.Time;
import com.traveltime.sdk.dto.requests.time.TimeType;
import com.traveltime.sdk.dto.requests.timefilter.ArrivalSearch;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.requests.timemap.Range;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.TimeMapResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import lombok.NonNull;
import lombok.val;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TravelTimeClient {
    @NonNull
    private final TravelTimeSDK sdk;

    public Either<TravelTimeError, TimeFilterResponse> getTimeFilter(
        Location search,
        List<Location> locations,
        Transportation transportation,
        int travelTime,
        Time time
    ) {
        TimeFilterRequest request = createRequest(
            search,
            locations,
            transportation,
            travelTime,
            time,
            Collections.singletonList(Property.TRAVEL_TIME),
            null
        );

        return sdk.send(request);
    }

    public Either<TravelTimeError, TimeFilterResponse> getTimeFilter(
        Location search,
        List<Location> locations,
        Transportation transportation,
        int travelTime,
        Time time,
        List<Property> properties,
        FullRange range
    ) {
        TimeFilterRequest request = createRequest(
            search,
            locations,
            transportation,
            travelTime,
            time,
            properties,
            range
        );

        return sdk.send(request);
    }

    public Either<TravelTimeError, TimeMapResponse> getTimeMap(
        Coordinates coordinates,
        Transportation transportation,
        Time time,
        int travelTime
    ) {
        TimeMapRequest request = createRequest(time, coordinates, transportation, travelTime, null);

        return sdk.send(request);
    }


    public TravelTimeClient(@NonNull TravelTimeCredentials credentials) {
        sdk = new TravelTimeSDK(credentials);
    }

    private TimeMapRequest createRequest(
        Time time,
        Coordinates coordinates,
        Transportation transportation,
        int travelTime,
        Range range
    ) {
        if (time.getTimeType() == TimeType.DEPARTURE) {
            val departureSearch = new com.traveltime.sdk.dto.requests.timemap.DepartureSearch(
                "Departure search",
                coordinates,
                transportation,
                time.getValue(),
                travelTime,
                range
            );

            return new TimeMapRequest(
                Collections.singletonList(departureSearch),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList()
            );
        } else {
            val arrivalSearch = new com.traveltime.sdk.dto.requests.timemap.ArrivalSearch(
                "Arrival search",
                coordinates,
                transportation,
                time.getValue(),
                travelTime,
                range
            );

            return new TimeMapRequest(
                Collections.emptyList(),
                Collections.singletonList(arrivalSearch),
                Collections.emptyList(),
                Collections.emptyList()
            );
        }
    }

    private TimeFilterRequest createRequest(
        Location search,
        List<Location> locations,
        Transportation transportation,
        int travelTime,
        Time time,
        List<Property> properties,
        FullRange range
    ) {
        val allLocations = Stream
            .concat(locations.stream(), Stream.of(search))
            .collect(Collectors.toList());
        if (time.getTimeType() == TimeType.DEPARTURE) {
            val departureSearch = new DepartureSearch(
                "Departure search",
                search.getId(),
                locations.stream().map(Location::getId).collect(Collectors.toList()),
                transportation,
                time.getValue(),
                travelTime,
                properties,
                range
            );

            return new TimeFilterRequest(
                allLocations,
                Collections.singletonList(departureSearch),
                Collections.emptyList()
            );
        } else {
            val arrivalSearch = new ArrivalSearch(
                "Arrival search",
                locations.stream().map(Location::getId).collect(Collectors.toList()),
                search.getId(),
                transportation,
                time.getValue(),
                travelTime,
                properties,
                range
            );
            return new TimeFilterRequest(
                allLocations,
                Collections.emptyList(),
                Collections.singletonList(arrivalSearch)
            );
        }
    }
}
