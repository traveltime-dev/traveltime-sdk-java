package com.traveltime.examples;

import com.traveltime.sdk.dto.common.Coordinates;
import javafx.util.Pair;

import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
}
