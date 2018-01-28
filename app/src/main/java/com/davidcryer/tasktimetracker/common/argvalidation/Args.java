package com.davidcryer.tasktimetracker.common.argvalidation;

import com.davidcryer.tasktimetracker.common.StringUtils;

public abstract class Args<E extends Args.Exception> {

    public void enforce() throws E {
        throwExceptionIfIllegal();
    }

    private void throwExceptionIfIllegal() throws E {
        if (hasFailedArg()) {
            throwException();
        }
    }

    protected abstract boolean hasFailedArg();

    private void throwException() throws E {
        throw exception();
    }

    protected abstract E exception();

    public static abstract class Exception extends RuntimeException {

        protected Exception(final String[] messages) {
            super(concatenate(messages));
        }

        private static String concatenate(final String[] messages) {
            return StringUtils.concatenate(messages, StringUtils.NEW_LINE);
        }
    }
}
