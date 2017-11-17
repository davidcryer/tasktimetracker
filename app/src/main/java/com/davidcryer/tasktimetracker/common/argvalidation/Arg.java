package com.davidcryer.tasktimetracker.common.argvalidation;

public class Arg {
    private final boolean passed;
    private final String errorMessage;

    public Arg(boolean passed, String errorMessage) {
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    boolean passed() {
        return passed;
    }

    String errorMessage() {
        return errorMessage;
    }

    public static void enforce(final boolean passed, final String errorMessage) {
        if (!passed) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
