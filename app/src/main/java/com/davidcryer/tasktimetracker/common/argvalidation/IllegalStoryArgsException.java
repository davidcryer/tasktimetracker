package com.davidcryer.tasktimetracker.common.argvalidation;

public class IllegalStoryArgsException extends IllegalArgsException {
    private final Args args;

    private IllegalStoryArgsException(Args args) {
        super(args.messages());
        this.args = args;
    }

    public Args args() {
        return args;
    }

    public static class Args implements IllegalArgsException.Args<IllegalStoryArgsException> {
        private final String idError;
        private final String titleError;

        private Args(String idError, String titleError) {
            this.idError = idError;
            this.titleError = titleError;
        }

        public boolean idIsIllegal() {
            return idError != null;
        }

        public boolean titleIsIllegal() {
            return titleError != null;
        }

        public String idError() {
            return idError;
        }

        public String titleError() {
            return titleError;
        }

        private String[] messages() {
            return new String[] {idError(), titleError()};
        }

        @Override
        public boolean hasFailedArg() {
            return idIsIllegal() || titleIsIllegal();
        }

        @Override
        public IllegalStoryArgsException exception() {
            return new IllegalStoryArgsException(this);
        }

        static class Builder {
            private String idError;
            private String titleError;

            Builder idError(final String error) {
                idError = error;
                return this;
            }

            Builder titleError(final String error) {
                titleError = error;
                return this;
            }

            Args create() {
                return new Args(titleError, idError);
            }
        }
    }
}
