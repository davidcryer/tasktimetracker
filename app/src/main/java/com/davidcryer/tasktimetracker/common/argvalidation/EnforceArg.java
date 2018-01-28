package com.davidcryer.tasktimetracker.common.argvalidation;

public class EnforceArg {

    public static void that(final boolean passed, final String errorMessage) throws IllegalArgumentException {
        if (!passed) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
