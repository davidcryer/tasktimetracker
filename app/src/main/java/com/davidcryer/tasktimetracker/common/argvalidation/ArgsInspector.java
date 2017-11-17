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
}
