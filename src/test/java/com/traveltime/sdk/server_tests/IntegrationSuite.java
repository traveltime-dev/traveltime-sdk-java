package com.traveltime.sdk.server_tests;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    SupportedLocationsTest.class,
    MapInfoTest.class,
    ProtoTimeFilterFastTest.class
})
public class IntegrationSuite {
    @BeforeClass
    public static void startSession() {
        TestSession.start();
    }

    @AfterClass
    public static void stopSession() {
        TestSession.stop();
    }
}
