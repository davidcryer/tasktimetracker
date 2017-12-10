package com.davidcryer.tasktimetracker.common;

public class StringUtils {
    public final static char ZERO = '0';
    public final static char DASH = '-';
    public final static char COLON = ':';
    public final static String NEW_LINE = "\n";

    private StringUtils() {}

    public static String concatenate(final String[] strings, final String delimiter) {
        if (strings.length == 0) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) {
                builder.append(delimiter);
            }
            builder.append(strings[i]);
        }
        return builder.toString();
    }

    public static String toMinTwoFigures(final long l) {
        final StringBuilder builder = new StringBuilder();
        if (l < 0) {
            builder.append(DASH);
        }
        if (Math.abs(l) < 10) {
            builder.append(ZERO);
        }
        builder.append(String.valueOf(l));
        return builder.toString();
    }
}
