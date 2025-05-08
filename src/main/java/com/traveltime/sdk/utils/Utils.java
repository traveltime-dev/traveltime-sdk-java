package com.traveltime.sdk.utils;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import okhttp3.HttpUrl;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String printableStackTrace(Throwable cause) {
        CharArrayWriter caw = new CharArrayWriter();
        cause.printStackTrace(new PrintWriter(caw));
        return caw.toString();
    }

    public static HttpUrl.Builder withQuery(HttpUrl.Builder builder, QueryElement... elems) {
        Arrays.stream(elems)
                .filter(QueryElement::isDefined)
                .forEach(q -> builder.addQueryParameter(q.getKey(), q.getValue()));
        return builder;
    }
}
