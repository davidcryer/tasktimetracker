package com.davidcryer.tasktimetracker.common.argvalidation;

public class OngoingSessionArgsBuilder {
    private final IllegalOngoingSessionArgsException.Args.Builder illegalArgsBuilder;

    public OngoingSessionArgsBuilder() {
        illegalArgsBuilder = new IllegalOngoingSessionArgsException.Args.Builder();
    }

    public OngoingSessionArgsBuilder start(final ArgsInspector.Arg arg) {
        if (!arg.passed()) {
            illegalArgsBuilder.startError(arg.errorMessage());
        }
        return this;
    }

    public IllegalOngoingSessionArgsException.Args args() {
        return illegalArgsBuilder.create();
    }
}
