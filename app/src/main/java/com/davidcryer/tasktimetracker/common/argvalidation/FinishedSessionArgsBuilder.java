package com.davidcryer.tasktimetracker.common.argvalidation;

public class FinishedSessionArgsBuilder implements ArgsBuilder<IllegalFinishedSessionArgsException, IllegalFinishedSessionArgsException.Args> {
    private final IllegalFinishedSessionArgsException.Args.Builder builder;

    public FinishedSessionArgsBuilder() {
        builder = new IllegalFinishedSessionArgsException.Args.Builder();
    }

    public FinishedSessionArgsBuilder id(final Arg arg) {
        if (!arg.passed()) {
            builder.idError(arg.errorMessage());
        }
        return this;
    }

    public FinishedSessionArgsBuilder start(final Arg arg) {
        if (!arg.passed()) {
            builder.startError(arg.errorMessage());
        }
        return this;
    }

    public FinishedSessionArgsBuilder finish(final Arg arg) {
        if (!arg.passed()) {
            builder.finishError(arg.errorMessage());
        }
        return this;
    }

    public FinishedSessionArgsBuilder timeline(final Arg arg) {
        if (!arg.passed()) {
            builder.timelineError(arg.errorMessage());
        }
        return this;
    }

    @Override
    public IllegalFinishedSessionArgsException.Args args() {
        return builder.create();
    }
}
