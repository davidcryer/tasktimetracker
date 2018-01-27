package com.davidcryer.tasktimetracker.common.argvalidation;

public class OngoingSessionArgsBuilder implements ArgsBuilder<IllegalOngoingSessionArgsException, IllegalOngoingSessionArgsException.Args> {
    private final IllegalOngoingSessionArgsException.Args.Builder illegalArgsBuilder;

    public OngoingSessionArgsBuilder() {
        illegalArgsBuilder = new IllegalOngoingSessionArgsException.Args.Builder();
    }

    public OngoingSessionArgsBuilder start(final Arg arg) {
        if (!arg.passed()) {
            illegalArgsBuilder.startError(arg.errorMessage());
        }
        return this;
    }

    @Override
    public IllegalOngoingSessionArgsException.Args args() {
        return illegalArgsBuilder.create();
    }
}
