package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.ArgRulesBuilder;
import com.davidcryer.argrules.Rule;

class TaskArgRulesBuilder implements ArgRulesBuilder<TaskArgRules> {
    private final TaskArgRules.Builder builder;

    TaskArgRulesBuilder() {
        builder = new TaskArgRules.Builder();
    }

    TaskArgRulesBuilder id(final Rule rule) {
        rule.checkSatisfied(builder::idError);
        return this;
    }

    TaskArgRulesBuilder title(final Rule rule) {
        rule.checkSatisfied(builder::titleError);
        return this;
    }

    TaskArgRulesBuilder ongoingSession(final Rule rule) {
        rule.checkSatisfied(builder::ongoingSessionError);
        return this;
    }

    @Override
    public TaskArgRules args() {
        return builder.create();
    }
}
