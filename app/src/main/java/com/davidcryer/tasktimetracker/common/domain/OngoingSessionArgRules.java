package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.ArgRules;

public class OngoingSessionArgRules extends ArgRules<OngoingSessionArgRules.Exception> {
    private final String startError;

    OngoingSessionArgRules(String startError) {
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
    protected boolean hasFailedRule() {
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

        OngoingSessionArgRules create() {
            return new OngoingSessionArgRules(startError);
        }
    }

    public static class Exception extends ArgRules.Exception {
        private final OngoingSessionArgRules args;

        private Exception(OngoingSessionArgRules args) {
            super(args.messages());
            this.args = args;
        }

        public OngoingSessionArgRules args() {
            return args;
        }
    }
}
