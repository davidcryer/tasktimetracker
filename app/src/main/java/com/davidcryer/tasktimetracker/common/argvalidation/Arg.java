package com.davidcryer.tasktimetracker.common.argvalidation;

public class Arg {
    private final boolean passed;
    private final String errorMessage;

    public Arg(boolean passed, String errorMessage) {
        this.passed = passed;
        this.errorMessage = errorMessage;
    }

    public void performCheck(final CheckCallback callback) {
        if (!passed) {
            callback.onFailure(errorMessage);
        }
    }

    public interface CheckCallback {
        void onFailure(String error);
    }
}
