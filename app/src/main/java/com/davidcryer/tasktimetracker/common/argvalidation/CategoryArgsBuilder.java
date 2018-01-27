package com.davidcryer.tasktimetracker.common.argvalidation;

public class CategoryArgsBuilder implements ArgsBuilder<IllegalCategoryArgsException.Args> {
    private final IllegalCategoryArgsException.Args.Builder illegalArgsBuilder;

    public CategoryArgsBuilder() {
        illegalArgsBuilder = new IllegalCategoryArgsException.Args.Builder();
    }

    public CategoryArgsBuilder id(final Arg idArg) {
        if (!idArg.passed()) {
            illegalArgsBuilder.idError(idArg.errorMessage());
        }
        return this;
    }

    public CategoryArgsBuilder title(final Arg titleArg) {
        if (!titleArg.passed()) {
            illegalArgsBuilder.titleError(titleArg.errorMessage());
        }
        return this;
    }

    @Override
    public IllegalCategoryArgsException.Args args() {
        return illegalArgsBuilder.create();
    }
}
