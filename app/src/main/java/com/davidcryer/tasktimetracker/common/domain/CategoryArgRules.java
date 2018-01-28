package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.ArgRules;

public class CategoryArgRules extends ArgRules<CategoryArgRules.Exception> {
    private final String idError;
    private final String titleError;

    private CategoryArgRules(String idError, String titleError) {
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
    protected boolean hasFailedRule() {
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

        CategoryArgRules create() {
            return new CategoryArgRules(titleError, idError);
        }
    }

    public static class Exception extends ArgRules.Exception {
        private final CategoryArgRules args;

        private Exception(CategoryArgRules args) {
            super(args.messages());
            this.args = args;
        }

        public CategoryArgRules args() {
            return args;
        }
    }
}
