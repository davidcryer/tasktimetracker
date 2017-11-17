package com.davidcryer.tasktimetracker.common.argvalidation;

public class TaskArgsBuilder {
    private final IllegalTaskArgsException.Args.Builder illegalArgsBuilder;

    public TaskArgsBuilder() {
        illegalArgsBuilder = new IllegalTaskArgsException.Args.Builder();
    }

    public TaskArgsBuilder id(final Arg arg) {
        if (!arg.passed()) {
            illegalArgsBuilder.idError(arg.errorMessage());
        }
        return this;
    }

    public TaskArgsBuilder title(final Arg arg) {
        if (!arg.passed()) {
            illegalArgsBuilder.titleError(arg.errorMessage());
        }
        return this;
    }

    public TaskArgsBuilder ongoingSession(final Arg arg) {
        if (!arg.passed()) {
            illegalArgsBuilder.ongoingSessionError(arg.errorMessage());
        }
        return this;
    }

    public IllegalTaskArgsException.Args args() {
        return illegalArgsBuilder.create();
    }
}
