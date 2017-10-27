package com.davidcryer.tasktimetracker.common.argvalidation;

public class IllegalFinishedSessionArgsException extends IllegalArgsException {
    private final Args args;

    private IllegalFinishedSessionArgsException(final Args args) {
        super(args.messages());
        this.args = args;
    }

    public Args args() {
        return args;
    }

    public static class Args implements IllegalArgsException.Args<IllegalFinishedSessionArgsException> {
        private final String idError;
        private final String startError;
        private final String finishError;
        private final String timelineError;

        private Args(String idError, String startError, String finishError, String timelineError) {
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
        public boolean hasFailedArg() {
            return idIsIllegal() || startIsIllegal() || finishIsIllegal() || timelineIsIllegal();
        }

        @Override
        public IllegalFinishedSessionArgsException exception() {
            return new IllegalFinishedSessionArgsException(this);
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

            Args create() {
                return new Args(idError, startError, finishError, timelineError);
            }
        }
    }
}
