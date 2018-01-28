package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Args;

public class FinishedSessionArgs extends Args<FinishedSessionArgs.Exception> {
    private final String idError;
    private final String startError;
    private final String finishError;
    private final String timelineError;

    private FinishedSessionArgs(String idError, String startError, String finishError, String timelineError) {
        this.idError = idError;
        this.startError = startError;
        this.finishError = finishError;
        this.timelineError = timelineError;
    }

    public boolean idIsIllegal() {
        return idError != null;
    }

    public boolean startIsIllegal() {
        return startError != null;
    }

    public boolean finishIsIllegal() {
        return finishError != null;
    }

    public boolean timelineIsIllegal() {
        return timelineError != null;
    }

    public String idError() {
        return idError;
    }

    public String startError() {
        return startError;
    }

    public String finishError() {
        return finishError;
    }

    public String timelineError() {
        return timelineError;
    }

    private String[] messages() {
        return new String[] {idError(), startError(), finishError(), timelineError()};
    }

    @Override
    protected boolean hasFailedArg() {
        return idIsIllegal() || startIsIllegal() || finishIsIllegal() || timelineIsIllegal();
    }

    @Override
    protected Exception exception() {
        return new Exception(this);
    }

    static class Builder {
        private String idError;
        private String startError;
        private String finishError;
        private String timelineError;

        Builder idError(final String error) {
            idError = error;
            return this;
        }

        Builder startError(final String error) {
            startError = error;
            return this;
        }

        Builder finishError(final String error) {
            finishError = error;
            return this;
        }

        Builder timelineError(final String error) {
            timelineError = error;
            return this;
        }

        FinishedSessionArgs create() {
            return new FinishedSessionArgs(idError, startError, finishError, timelineError);
        }
    }

    public static class Exception extends Args.Exception {
        private final FinishedSessionArgs args;

        private Exception(FinishedSessionArgs args) {
            super(args.messages());
            this.args = args;
        }

        public FinishedSessionArgs args() {
            return args;
        }
    }
}
