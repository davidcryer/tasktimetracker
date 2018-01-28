package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsBuilder;

class FinishedSessionArgsBuilder implements ArgsBuilder<FinishedSessionArgs> {
    private final FinishedSessionArgs.Builder builder;

    FinishedSessionArgsBuilder() {
        builder = new FinishedSessionArgs.Builder();
    }

    FinishedSessionArgsBuilder id(final Arg arg) {
        arg.performCheck(builder::idError);
        return this;
    }

    FinishedSessionArgsBuilder start(final Arg arg) {
        arg.performCheck(builder::startError);
        return this;
    }

    FinishedSessionArgsBuilder finish(final Arg arg) {
        arg.performCheck(builder::finishError);
        return this;
    }

    FinishedSessionArgsBuilder timeline(final Arg arg) {
        arg.performCheck(builder::timelineError);
        return this;
    }

    @Override
    public FinishedSessionArgs args() {
        return builder.create();
    }
}
