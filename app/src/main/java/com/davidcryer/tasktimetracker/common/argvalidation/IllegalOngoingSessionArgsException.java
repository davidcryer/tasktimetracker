package com.davidcryer.tasktimetracker.common.argvalidation;

public class IllegalOngoingSessionArgsException extends IllegalArgsException {
    private final Args args;

    private IllegalOngoingSessionArgsException(Args args) {
        super(args.messages());
        this.args = args;
    }

    public Args args() {
        return args;
    }

    public static class Args implements IllegalArgsException.Args<IllegalOngoingSessionArgsException> {
        private final String startError;

        private Args(String startError) {
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
        public boolean hasFailedArg() {
            return startIsIllegal();
        }

        @Override
        public IllegalOngoingSessionArgsException exception() {
            return new IllegalOngoingSessionArgsException(this);
        }

        static class Builder {
            private String startError;

            Builder startError(final String error) {
                startError = error;
                return this;
            }

            Args create() {
                return new Args(startError);
            }
        }
    }
}
