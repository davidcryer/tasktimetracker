package com.davidcryer.tasktimetracker.common;

public class ObjectUtils {

    private ObjectUtils() {}

    public static boolean equalAllowNull(final Object o1, final Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }
}
