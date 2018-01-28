package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.ArgRules;

public class TaskArgRules extends ArgRules<TaskArgRules.Exception> {
    private final String idError;
    private final String titleError;
    private final String ongoingSessionError;

    private TaskArgRules(String idError, String titleError, String ongoingSessionError) {
        this.idError = idError;
        this.titleError = titleError;
        this.ongoingSessionError = ongoingSessionError;
    }

    public boolean idIsIllegal() {
        return idError != null;
    }

    public boolean titleIsIllegal() {
        return titleError != null;
    }

    public boolean ongoingSessionIsIllegal() {
        return ongoingSessionError != null;
    }

    public String idError() {
        return idError;
    }

    public String titleError() {
        return titleError;
    }

    public String ongoingSessionError() {
        return ongoingSessionError;
    }

    private String[] messages() {
        return new String[] {idError(), titleError(), ongoingSessionError()};
    }

    @Override
    protected boolean hasFailedRule() {
        return idIsIllegal() || titleIsIllegal() || ongoingSessionIsIllegal();
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private String idError;
        private String titleError;
        private String ongoingSessionError;

        Builder idError(final String error) {
            idError = error;
            return this;
        }

        Builder titleError(final String error) {
            titleError = error;
            return this;
        }

        Builder ongoingSessionError(final String error) {
            ongoingSessionError = error;
            return this;
        }

        TaskArgRules create() {
            return new TaskArgRules(titleError, idError, ongoingSessionError);
        }
    }

    public static class Exception extends ArgRules.Exception {
        private final TaskArgRules args;

        private Exception(TaskArgRules args) {
            super(args.messages());
            this.args = args;
        }

        public TaskArgRules args() {
            return args;
        }
    }
}
