package com.davidcryer.tasktimetracker.common.argvalidation;

public class IllegalTaskArgsException extends IllegalArgsException {
    private final Args args;

    private IllegalTaskArgsException(Args args) {
        super(args.messages());
        this.args = args;
    }

    public IllegalTaskArgsException.Args args() {
        return args;
    }

    public static class Args implements IllegalArgsException.Args<IllegalTaskArgsException> {
        private final String idError;
        private final String titleError;
        private final String ongoingSessionError;

        private Args(String idError, String titleError, String ongoingSessionError) {
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
        public boolean hasFailedArg() {
            return idIsIllegal() || titleIsIllegal() || ongoingSessionIsIllegal();
        }

        @Override
        public IllegalTaskArgsException exception() {
            return new IllegalTaskArgsException(this);
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

            Args create() {
                return new Args(titleError, idError, ongoingSessionError);
            }
        }
    }
}
