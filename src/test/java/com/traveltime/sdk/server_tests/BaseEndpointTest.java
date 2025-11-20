package com.traveltime.sdk.server_tests;

import com.traveltime.sdk.server_tests.values.Endpoint;
import org.junit.*;

public abstract class BaseEndpointTest {

    protected TestServer.InSession server;
    protected ApiEndpointTestRunner runner;

    @BeforeClass
    public static void startSession() {
        TestSession.start();
    }

    @AfterClass
    public static void stopSession() {
        TestSession.stop();
    }

    @Before
    public void setUpRunner() {
        server = TestSession.getServer();
        runner = new ApiEndpointTestRunner(server, endpoint());
    }

    protected abstract Endpoint endpoint();
}
