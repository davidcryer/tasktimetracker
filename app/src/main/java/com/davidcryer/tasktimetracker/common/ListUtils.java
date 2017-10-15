package com.davidcryer.tasktimetracker.common;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    private ListUtils() {}

    @NonNull
    public static <T> List<T> newList(final List<T> list) {
        return list == null ? new ArrayList<T>() : new ArrayList<>(list);
    }
}
