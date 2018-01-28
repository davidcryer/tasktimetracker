package com.davidcryer.tasktimetracker.common.argvalidation;

public class Rule {
    private final boolean isSatisfied;
    private final String errorMessage;

    public Rule(boolean isSatisfied, String errorMessage) {
        this.isSatisfied = isSatisfied;
        this.errorMessage = errorMessage;
    }

    public void checkSatisfied(final CheckCallback callback) {
        if (!isSatisfied) {
            callback.notSatisfied(errorMessage);
        }
    }

    public interface CheckCallback {
        void notSatisfied(String error);
    }
}
