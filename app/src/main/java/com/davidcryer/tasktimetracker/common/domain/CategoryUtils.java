package com.davidcryer.tasktimetracker.common.domain;

import java.util.Comparator;
import java.util.List;

public class CategoryUtils {

    private CategoryUtils() {}

    public static void sortAlphabetically(final List<Category> categories) {
        categories.sort(Comparator.comparing(Category::title));
    }
}
