package com.davidcryer.tasktimetracker.common;

public class IllegalArgsException extends RuntimeException {

    public IllegalArgsException(final String[] messages) {
        super(concatenate(messages));
    }

    private static String concatenate(final String[] messages) {
        return StringUtils.concatenate(messages, StringUtils.NEW_LINE);
    }
}
