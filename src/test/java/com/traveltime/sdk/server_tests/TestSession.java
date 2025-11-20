package com.traveltime.sdk.server_tests;


import io.vavr.control.Option;

public final class TestSession {
    private static final TestServer server = new TestServer();

    private static volatile Option<TestServer.InSession> sessionServer = Option.none();
    private static volatile long startCounter = 0L;

    public static void start() {
        synchronized (server) {
            if (sessionServer.isEmpty()) {
                sessionServer = Option.some(server.executeStartSessionReq());
            }
            startCounter += 1;
        }
    }

    public static void stop() {
        synchronized (server) {
            if (startCounter > 0L) {
                startCounter -= 1;
            }
            if (startCounter == 0L) {
                sessionServer.forEach(server -> {
                    TestSession.sessionServer = Option.none();
                    server.executeStopSessionReq();
                });
            }
        }

    }

    public static TestServer.InSession getServer() {
        return sessionServer.getOrElseThrow(() -> new RuntimeException("Test session not initialized"));
    }
}
