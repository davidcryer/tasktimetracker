package com.davidcryer.tasktimetracker.common;

import java.util.ArrayList;
import java.util.List;

public class ArgsInspector {

    private ArgsInspector() {}

    public static void inspect(final ArgCheck... checks) throws IllegalArgsException {
        throwExceptionIfCheckFailed(checks);
    }

    private static void throwExceptionIfCheckFailed(final ArgCheck[] checks) throws IllegalArgsException {
        if (failedCheckExists(checks)) {
            throwException(failureMessages(checks));
        }
    }

    private static boolean failedCheckExists(final ArgCheck[] checks) {
        for (final ArgCheck check : checks) {
            if (!check.passed()) {
                return true;
            }
        }
        return false;
    }

    private static String[] failureMessages(final ArgCheck[] checks) {
        final List<String> failureMessages = new ArrayList<>(checks.length);
        for (final ArgCheck check : checks) {
            if (!check.passed()) {
                failureMessages.add(check.failureMessage());
            }
        }
        return failureMessages.toArray(new String[failureMessages.size()]);
    }

    private static void throwException(final String[] failureMessages) {
        throw new IllegalArgsException(failureMessages);
    }

    public static ArgCheck check(final ArgCriteria criteria, final String failureMessage) {
        return new ArgCheck(criteria.passed(), failureMessage);
    }

    public static class ArgCheck {
        private final boolean passed;
        private final String failureMessage;

        private ArgCheck(boolean passed, String failureMessage) {
            this.passed = passed;
            this.failureMessage = failureMessage;
        }

        private boolean passed() {
            return passed;
        }

        private String failureMessage() {
            return failureMessage;
        }
    }

    public interface ArgCriteria {
        boolean passed();
    }
}
