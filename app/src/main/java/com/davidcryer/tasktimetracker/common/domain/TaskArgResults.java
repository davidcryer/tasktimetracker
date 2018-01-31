package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.multiarg.BrokenRulesException;
import com.davidcryer.argrules.multiarg.Result;
import com.davidcryer.argrules.multiarg.Results;

public class TaskArgResults extends Results<TaskArgResults.Exception> {
    private final Result id;
    private final Result title;
    private final Result ongoingSession;

    private TaskArgResults(Result id, Result title, Result ongoingSession) {
        this.id = id;
        this.title = title;
        this.ongoingSession = ongoingSession;
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

    public Result ongoingSession() {
        return ongoingSession;
    }

    @Override
    protected Result[] asArray() {
        return new Result[] {id(), title(), ongoingSession()};
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private Result id;
        private Result title;
        private Result ongoingSession;

        Builder id(final Result id) {
            this.id = id;
            return this;
        }

        Builder title(final Result title) {
            this.title = title;
            return this;
        }

        Builder ongoingSession(final Result ongoingSession) {
            this.ongoingSession = ongoingSession;
            return this;
        }

        TaskArgResults create() {
            return new TaskArgResults(id, title, ongoingSession);
        }
    }

    public static class Exception extends BrokenRulesException {
        private final TaskArgResults results;

        private Exception(TaskArgResults results) {
            super(results);
            this.results = results;
        }

        public TaskArgResults results() {
            return results;
        }
    }
}
