package com.traveltime.examples;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.responses.proto.ProtoResult;
import com.traveltime.sdk.dto.responses.timefilter.ResponseLocation;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    private static final Random random = new Random();

    private static Coordinates generateCoordinates(Coordinates center, double range) {
        return new Coordinates(generateDouble(center.getLat(), range), generateDouble(center.getLng(), range));
    }

    private static double generateDouble(double center, double range) {
        return center + (random.nextDouble() - 0.5) * range;
    }

    public static List<Location> generateLocations(
        String name,
        Coordinates center,
        double range,
        int size
    ) {
        return IntStream
            .range(0, size)
            .mapToObj(i -> new Location(name + " " + i, generateCoordinates(center, range)))
            .collect(Collectors.toList());
    }


    public static List<String> findClosest(List<ResponseLocation> locations, int top) {
        return locations
            .stream()
            .sorted(Comparator.comparing(left -> left.getProperties().get(0).getTravelTime()))
            .map(ResponseLocation::getId)
            .collect(Collectors.toList())
            .subList(0, top);
    }


    public static List<String> findClosestProto(
        List<ProtoResult> protoResults,
        int top
    ) {
        return protoResults
            .stream()
            .sorted(Comparator.comparing(ProtoResult::getTravelTime))
            .map(ProtoResult::getDestinationId)
            .collect(Collectors.toList())
            .subList(0, top);
    }
}