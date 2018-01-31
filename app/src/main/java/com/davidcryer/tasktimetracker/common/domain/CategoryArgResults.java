package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.BrokenRulesException;
import com.davidcryer.argrules.multiarg.Result;
import com.davidcryer.argrules.multiarg.Results;

public class CategoryArgResults extends Results<CategoryArgResults.Exception> {
    private final Result id;
    private final Result title;

    private CategoryArgResults(Result id, Result title) {
        this.id = id;
        this.title = title;
    }

    public boolean hasTitleError() {
        return title != null && !title.passed();
    }

    public Result id() {
        return id;
    }

    public Result title() {
        return title;
    }

    @Override
    protected Result[] asArray() {
        return new Result[] {id(), title()};
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder implements Results.Builder<CategoryArgResults> {
        private Result id;
        private Result title;

        Builder id(final Result id) {
            this.id = id;
            return this;
        }

        Builder title(final Result title) {
            this.title = title;
            return this;
        }

        @Override
        public CategoryArgResults results() {
            return new CategoryArgResults(id, title);
        }
    }

    public static class Exception extends BrokenRulesException {
        private final CategoryArgResults results;

        private Exception(CategoryArgResults results) {
            super(results);
            this.results = results;
        }

        public CategoryArgResults results() {
            return results;
        }
    }
}
