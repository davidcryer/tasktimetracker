package com.davidcryer.tasktimetracker.common;

public class StringUtils {
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
}
