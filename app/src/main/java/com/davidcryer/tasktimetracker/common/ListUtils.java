package com.davidcryer.tasktimetracker.common;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    private ListUtils() {}

    public static <T> List<T> emptyIfNull(final List<T> list) {
        return list == null ? new ArrayList<T>() : list;
    }
}
