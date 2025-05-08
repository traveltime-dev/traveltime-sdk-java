package com.traveltime.sdk.utils;

public class Version {
    private Version() {
        throw new IllegalStateException("Utility class");
    }

    public static String getVersion() {
        return Version.class.getPackage().getImplementationVersion();
    }
}
