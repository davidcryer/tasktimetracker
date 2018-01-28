package com.davidcryer.tasktimetracker.common.argvalidation;

public class EnforceRule {

    public static void that(final boolean isSatisfied, final String errorMessage) throws IllegalArgumentException {
        if (!isSatisfied) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
