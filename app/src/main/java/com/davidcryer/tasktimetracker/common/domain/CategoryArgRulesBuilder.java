package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.ArgRulesBuilder;
import com.davidcryer.argrules.Rule;

class CategoryArgRulesBuilder implements ArgRulesBuilder<CategoryArgRules> {
    private final CategoryArgRules.Builder builder;

    CategoryArgRulesBuilder() {
        builder = new CategoryArgRules.Builder();
    }

    CategoryArgRulesBuilder id(final Rule rule) {
        rule.checkSatisfied(builder::idError);
        return this;
    }

    CategoryArgRulesBuilder title(final Rule rule) {
        rule.checkSatisfied(builder::titleError);
        return this;
    }

    @Override
    public CategoryArgRules args() {
        return builder.create();
    }
}
