package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Args;

public class TaskArgs extends Args<TaskArgs.Exception> {
    private final String idError;
    private final String titleError;
    private final String ongoingSessionError;

    private TaskArgs(String idError, String titleError, String ongoingSessionError) {
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
    protected boolean hasFailedArg() {
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

        TaskArgs create() {
            return new TaskArgs(titleError, idError, ongoingSessionError);
        }
    }

    public static class Exception extends Args.Exception {
        private final TaskArgs args;

        private Exception(TaskArgs args) {
            super(args.messages());
            this.args = args;
        }

        public TaskArgs args() {
            return args;
        }
    }
}
