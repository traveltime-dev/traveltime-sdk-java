package com.traveltime.examples;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.timefilterfast.Location;
import java.util.*;
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

    public static List<Map.Entry<String, Coordinates>> generateLocations(
            String name, Coordinates center, double range, int size) {
        List<Map.Entry<String, Coordinates>> locations = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            locations.add(new AbstractMap.SimpleEntry<>(name + " " + i, generateCoordinates(center, range)));
        }
        return locations;
    }

    public static List<String> findClosest(List<Location> locations, int top) {
        return locations.stream()
                .sorted(Comparator.comparing(left -> left.getProperties().getTravelTime()))
                .map(Location::getId)
                .collect(Collectors.toList())
                .subList(0, top);
    }

    public static List<Coordinates> findClosest(List<Integer> travelTimes, List<Coordinates> locations, int top) {
        return IntStream.range(0, Math.min(travelTimes.size(), locations.size()))
                .mapToObj(i -> new AbstractMap.SimpleEntry<>(locations.get(i), travelTimes.get(i)))
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList())
                .subList(0, top);
    }
}
