package com.davidcryer.tasktimetracker.common;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtils {

    private ListUtils() {}

    @NonNull
    public static <T> List<T> newList(final List<T> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    public static <T> List<T> remove(final List<T> list, final int index, final int count) {
        final List<T> removedElements = new ArrayList<>(count);
        final Iterator<T> itr = list.iterator();
        int i = 0;
        while (i < index) {
            itr.next();
        }
        while (i < index + count) {
            removedElements.add(itr.next());
            itr.remove();
        }
        return removedElements;
    }
}
