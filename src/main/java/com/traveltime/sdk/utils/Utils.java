package com.traveltime.sdk.utils;

import lombok.SneakyThrows;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String printableStackTrace(Throwable cause) {
        CharArrayWriter caw = new CharArrayWriter();
        cause.printStackTrace(new PrintWriter(caw));
        return caw.toString();
    }

    public static String composeQuery(QueryElement... elems) {
        return Arrays.stream(elems)
                .filter(QueryElement::isDefined)
                .map(QueryElement::toString)
                .collect(Collectors.joining("&"));
    }

    @SneakyThrows
    public static URI withQuery(URI uri, String query) {
        return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), query, uri.getFragment());
    }

}
