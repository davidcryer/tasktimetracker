package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Args;

public class CategoryArgs extends Args<CategoryArgs.Exception> {
    private final String idError;
    private final String titleError;

    private CategoryArgs(String idError, String titleError) {
        this.idError = idError;
        this.titleError = titleError;
    }

    public boolean idIsIllegal() {
        return idError != null;
    }

    public boolean titleIsIllegal() {
        return titleError != null;
    }

    public String idError() {
        return idError;
    }

    public String titleError() {
        return titleError;
    }

    String[] messages() {
        return new String[] {idError(), titleError()};
    }

    @Override
    protected boolean hasFailedArg() {
        return idIsIllegal() || titleIsIllegal();
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private String idError;
        private String titleError;

        Builder idError(final String error) {
            idError = error;
            return this;
        }

        Builder titleError(final String error) {
            titleError = error;
            return this;
        }

        CategoryArgs create() {
            return new CategoryArgs(titleError, idError);
        }
    }

    public static class Exception extends Args.Exception {
        private final CategoryArgs args;

        private Exception(CategoryArgs args) {
            super(args.messages());
            this.args = args;
        }

        public CategoryArgs args() {
            return args;
        }
    }
}
