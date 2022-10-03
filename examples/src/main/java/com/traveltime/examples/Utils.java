package com.traveltime.examples;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.timefilterfast.Location;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    private static Random rd = new Random();

    private static Coordinates generateCoordinates(Coordinates center, double range) {
        return new Coordinates(generateDouble(center.getLat(), range), generateDouble(center.getLng(), range));
    }

    private static double generateDouble(double center, double range) {
        return center + (rd.nextDouble() - 0.5) * range;
    }

    public static List<Pair<String, Coordinates>> generateLocations(
        String name,
        Coordinates center,
        double range,
        int size
    ) {
        List<Pair<String, Coordinates>> locations = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            locations.add(new Pair<>(name + " " + i, generateCoordinates(center, range)));
        }
        return locations;
    }

    public static List<String> findClosest(List<Location> locations, int top) {
        return locations
                .stream()
                .sorted(Comparator.comparing(left -> left.getProperties().getTravelTime()))
                .map(Location::getId)
                .collect(Collectors.toList())
                .subList(0, top);
    }

    public static List<String> findClosest(
            List<Integer> travelTimes,
            List<Pair<String, Coordinates>> locations,
            int top
    ) {
        return IntStream
                .range(0, Math.min(travelTimes.size(), locations.size()))
                .mapToObj(i -> new Pair<>(locations.get(i).getKey(), travelTimes.get(i)))
                .sorted(Comparator.comparing(Pair::getValue))
                .map(Pair::getKey)
                .collect(Collectors.toList())
                .subList(0, top);
    }
}
