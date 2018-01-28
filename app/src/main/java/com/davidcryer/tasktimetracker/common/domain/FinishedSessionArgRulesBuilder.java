package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Rule;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgRulesBuilder;

class FinishedSessionArgRulesBuilder implements ArgRulesBuilder<FinishedSessionArgRules> {
    private final FinishedSessionArgRules.Builder builder;

    FinishedSessionArgRulesBuilder() {
        builder = new FinishedSessionArgRules.Builder();
    }

    FinishedSessionArgRulesBuilder id(final Rule rule) {
        rule.checkSatisfied(builder::idError);
        return this;
    }

    FinishedSessionArgRulesBuilder start(final Rule rule) {
        rule.checkSatisfied(builder::startError);
        return this;
    }

    FinishedSessionArgRulesBuilder finish(final Rule rule) {
        rule.checkSatisfied(builder::finishError);
        return this;
    }

    FinishedSessionArgRulesBuilder timeline(final Rule rule) {
        rule.checkSatisfied(builder::timelineError);
        return this;
    }

    @Override
    public FinishedSessionArgRules args() {
        return builder.create();
    }
}
