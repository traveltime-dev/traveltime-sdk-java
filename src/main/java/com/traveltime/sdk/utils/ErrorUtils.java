package com.traveltime.sdk.utils;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

public class ErrorUtils {
    private ErrorUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String printableStackTrace(Throwable cause) {
        CharArrayWriter caw = new CharArrayWriter();
        cause.printStackTrace(new PrintWriter(caw));
        return caw.toString();
    }
}
