package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.BrokenRulesException;
import com.davidcryer.argrules.multiarg.Result;
import com.davidcryer.argrules.multiarg.Results;

public class FinishedSessionArgResults extends Results<FinishedSessionArgResults.Exception> {
    private final Result id;
    private final Result start;
    private final Result finish;
    private final Result timeline;

    private FinishedSessionArgResults(Result id, Result start, Result finish, Result timeline) {
        this.id = id;
        this.start = start;
        this.finish = finish;
        this.timeline = timeline;
    }

    public Result id() {
        return id;
    }

    public Result start() {
        return start;
    }

    public Result finish() {
        return finish;
    }

    public Result timeline() {
        return timeline;
    }

    @Override
    protected Result[] asArray() {
        return new Result[] {id(), start(), finish(), timeline()};
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private Result id;
        private Result start;
        private Result finish;
        private Result timeline;

        Builder id(final Result id) {
            this.id = id;
            return this;
        }

        Builder start(final Result start) {
            this.start = start;
            return this;
        }

        Builder finish(final Result finish) {
            this.finish = finish;
            return this;
        }

        Builder timeline(final Result timeline) {
            this.timeline = timeline;
            return this;
        }

        FinishedSessionArgResults create() {
            return new FinishedSessionArgResults(id, start, finish, timeline);
        }
    }

    public static class Exception extends BrokenRulesException {
        private final FinishedSessionArgResults args;

        private Exception(FinishedSessionArgResults args) {
            super(args);
            this.args = args;
        }

        public FinishedSessionArgResults args() {
            return args;
        }
    }
}
