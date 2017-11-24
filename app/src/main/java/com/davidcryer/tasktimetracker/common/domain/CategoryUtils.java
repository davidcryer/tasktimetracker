package com.davidcryer.tasktimetracker.common.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryUtils {

    private CategoryUtils() {}

    public static void sortAlphabetically(final List<Category> categories) {
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category l, Category r) {
                return l.title().compareTo(r.title());
            }
        });
    }
}
