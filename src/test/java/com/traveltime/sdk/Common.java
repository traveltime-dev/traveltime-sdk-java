package com.traveltime.sdk;

import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Assert;

public class Common {
    public static String readFile(String fileName) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/" + fileName))) {
            return stream.collect(Collectors.joining("\n"));
        }
    }

    public static List<Coordinates> generateCoordinates(int size) {
        Random r = new Random();
        List<Coordinates> list = new ArrayList<>();
        for (int i = 0; i < size; i++) list.add(new Coordinates(r.nextDouble(), r.nextDouble()));
        return list;
    }

    public static <T> void assertResponseIsRight(Either<TravelTimeError, T> response) {
        response.peekLeft(error -> {
            System.out.println("Error: " + error.getMessage());
            if (error.retrieveCause().isDefined()) {
                System.out.println("Cause: " + error.retrieveCause().get().getMessage());
            }
        });
        Assert.assertTrue(response.isRight());
    }
}
