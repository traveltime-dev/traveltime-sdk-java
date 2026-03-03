package com.traveltime.sdk;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * JUnit rule that retries a test up to a specified number of times.
 * Use on tests that hit external services and may fail due to transient network issues.
 */
public class RetryRule implements TestRule {
    private final int maxAttempts;

    public RetryRule(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable lastFailure = null;
                for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                    try {
                        base.evaluate();
                        return;
                    } catch (Throwable t) {
                        lastFailure = t;
                        System.out.printf(
                                "[RetryRule] %s: attempt %d/%d failed: %s%n",
                                description.getDisplayName(), attempt, maxAttempts, t);
                    }
                }
                throw lastFailure;
            }
        };
    }
}
