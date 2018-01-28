package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Rule;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgRulesBuilder;

class OngoingSessionArgRulesBuilder implements ArgRulesBuilder<OngoingSessionArgRules> {
    private final OngoingSessionArgRules.Builder builder;

    OngoingSessionArgRulesBuilder() {
        builder = new OngoingSessionArgRules.Builder();
    }

    OngoingSessionArgRulesBuilder start(final Rule rule) {
        rule.checkSatisfied(builder::startError);
        return this;
    }

    @Override
    public OngoingSessionArgRules args() {
        return builder.create();
    }
}
