package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsBuilder;

class OngoingSessionArgsBuilder implements ArgsBuilder<OngoingSessionArgs> {
    private final OngoingSessionArgs.Builder builder;

    OngoingSessionArgsBuilder() {
        builder = new OngoingSessionArgs.Builder();
    }

    OngoingSessionArgsBuilder start(final Arg arg) {
        arg.performCheck(builder::startError);
        return this;
    }

    @Override
    public OngoingSessionArgs args() {
        return builder.create();
    }
}
