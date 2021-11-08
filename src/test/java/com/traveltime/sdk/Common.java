package com.traveltime.sdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Common {
    public static String readFile(String fileName) throws IOException {
        try(Stream<String> stream = Files.lines(Paths.get("src/test/resources/" + fileName))) {
            return stream.collect(Collectors.joining("\n"));
        }
    }
}
