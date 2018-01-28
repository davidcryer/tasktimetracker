package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsBuilder;

class CategoryArgsBuilder implements ArgsBuilder<CategoryArgs> {
    private final CategoryArgs.Builder builder;

    CategoryArgsBuilder() {
        builder = new CategoryArgs.Builder();
    }

    CategoryArgsBuilder id(final Arg idArg) {
        idArg.performCheck(builder::idError);
        return this;
    }

    CategoryArgsBuilder title(final Arg titleArg) {
        titleArg.performCheck(builder::titleError);
        return this;
    }

    @Override
    public CategoryArgs args() {
        return builder.create();
    }
}
