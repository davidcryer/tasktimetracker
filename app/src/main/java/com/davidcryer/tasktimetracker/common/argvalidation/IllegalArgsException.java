package com.davidcryer.tasktimetracker.common.argvalidation;

import com.davidcryer.tasktimetracker.common.StringUtils;

class IllegalArgsException extends RuntimeException {

    IllegalArgsException(final String[] messages) {
        super(concatenate(messages));
    }

    private static String concatenate(final String[] messages) {
        return StringUtils.concatenate(messages, StringUtils.NEW_LINE);
    }

    interface Args<E extends IllegalArgsException> {
        boolean hasFailedArg();
        E exception();
    }
}
