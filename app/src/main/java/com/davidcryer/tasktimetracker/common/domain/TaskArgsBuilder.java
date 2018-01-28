package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsBuilder;

class TaskArgsBuilder implements ArgsBuilder<TaskArgs> {
    private final TaskArgs.Builder builder;

    TaskArgsBuilder() {
        builder = new TaskArgs.Builder();
    }

    TaskArgsBuilder id(final Arg arg) {
        arg.performCheck(builder::idError);
        return this;
    }

    TaskArgsBuilder title(final Arg arg) {
        arg.performCheck(builder::titleError);
        return this;
    }

    TaskArgsBuilder ongoingSession(final Arg arg) {
        arg.performCheck(builder::ongoingSessionError);
        return this;
    }

    @Override
    public TaskArgs args() {
        return builder.create();
    }
}
