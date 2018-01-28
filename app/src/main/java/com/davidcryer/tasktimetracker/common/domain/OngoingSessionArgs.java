package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Args;

public class OngoingSessionArgs extends Args<OngoingSessionArgs.Exception> {
    private final String startError;

    OngoingSessionArgs(String startError) {
        this.startError = startError;
    }

    public boolean startIsIllegal() {
        return startError != null;
    }

    public String startError() {
        return startError;
    }

    private String[] messages() {
        return new String[] {startError()};
    }

    @Override
    protected boolean hasFailedArg() {
        return startIsIllegal();
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private String startError;

        Builder startError(final String error) {
            startError = error;
            return this;
        }

        OngoingSessionArgs create() {
            return new OngoingSessionArgs(startError);
        }
    }

    public static class Exception extends Args.Exception {
        private final OngoingSessionArgs args;

        private Exception(OngoingSessionArgs args) {
            super(args.messages());
            this.args = args;
        }

        public OngoingSessionArgs args() {
            return args;
        }
    }
}
