package com.davidcryer.tasktimetracker.common.argvalidation;

public class ArgsInspector {

    private ArgsInspector() {}

    public static <E extends IllegalArgsException> void inspect(final IllegalArgsException.Args<E> args) throws E {
        throwExceptionIfCheckFailed(args);
    }

    private static <E extends IllegalArgsException> void throwExceptionIfCheckFailed(final IllegalArgsException.Args<E> args) throws E {
        if (args.hasFailedArg()) {
            throwException(args);
        }
    }

    private static <E extends IllegalArgsException> void throwException(final IllegalArgsException.Args<E> args) {
        throw args.exception();
    }

    public static class Arg {
        private final boolean passed;
        private final String errorMessage;

        public Arg(boolean passed, String errorMessage) {
            this.passed = passed;
            this.errorMessage = errorMessage;
        }

        boolean passed() {
            return passed;
        }

        String errorMessage() {
            return errorMessage;
        }
    }
}
