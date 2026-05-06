package com.traveltime.sdk.utils;

import java.util.Arrays;
import okhttp3.HttpUrl;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static String printableStackTrace(Throwable cause) {
        StringBuilder sb = new StringBuilder();
        sb.append(cause.toString());
        for (StackTraceElement element : cause.getStackTrace()) {
            sb.append("\n\tat ").append(element.toString());
        }
        Throwable causedBy = cause.getCause();
        while (causedBy != null) {
            sb.append("\nCaused by: ").append(causedBy.toString());
            for (StackTraceElement element : causedBy.getStackTrace()) {
                sb.append("\n\tat ").append(element.toString());
            }
            causedBy = causedBy.getCause();
        }
        return sb.toString();
    }

    public static HttpUrl.Builder withQuery(HttpUrl.Builder builder, QueryElement... elems) {
        Arrays.stream(elems)
                .filter(QueryElement::isDefined)
                .forEach(q -> builder.addQueryParameter(q.getKey(), q.getValue()));
        return builder;
    }
}
