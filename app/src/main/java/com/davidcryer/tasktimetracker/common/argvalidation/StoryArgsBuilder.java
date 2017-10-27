package com.davidcryer.tasktimetracker.common.argvalidation;

public class StoryArgsBuilder {
    private final IllegalStoryArgsException.Args.Builder illegalArgsBuilder;

    public StoryArgsBuilder() {
        illegalArgsBuilder = new IllegalStoryArgsException.Args.Builder();
    }

    public StoryArgsBuilder id(final ArgsInspector.Arg idArg) {
        if (!idArg.passed()) {
            illegalArgsBuilder.idError(idArg.errorMessage());
        }
        return this;
    }

    public StoryArgsBuilder title(final ArgsInspector.Arg titleArg) {
        if (!titleArg.passed()) {
            illegalArgsBuilder.titleError(titleArg.errorMessage());
        }
        return this;
    }

    public IllegalStoryArgsException.Args args() {
        return illegalArgsBuilder.create();
    }
}
