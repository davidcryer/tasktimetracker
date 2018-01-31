package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.BrokenRulesException;
import com.davidcryer.argrules.multiarg.Result;
import com.davidcryer.argrules.multiarg.Results;

public class OngoingSessionArgResults extends Results<OngoingSessionArgResults.Exception> {
    private final Result start;

    OngoingSessionArgResults(Result start) {
        this.start = start;
    }

    public Result start() {
        return start;
    }

    @Override
    protected Result[] asArray() {
        return new Result[] {start()};
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private Result start;

        Builder start(final Result start) {
            this.start = start;
            return this;
        }

        OngoingSessionArgResults create() {
            return new OngoingSessionArgResults(start);
        }
    }

    public static class Exception extends BrokenRulesException {
        private final OngoingSessionArgResults results;

        private Exception(OngoingSessionArgResults results) {
            super(results);
            this.results = results;
        }

        public OngoingSessionArgResults results() {
            return results;
        }
    }
}
