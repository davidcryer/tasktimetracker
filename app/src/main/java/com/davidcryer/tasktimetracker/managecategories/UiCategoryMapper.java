package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.domain.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class UiCategoryMapper {

    private UiCategoryMapper() {}

    static List<UiListItem> from(final List<Category> categories) {
        final List<UiListItem> uiListItems = new LinkedList<>();
        for (final Category category : categories) {
            uiListItems.addAll(from(category));
        }
        return uiListItems;
    }

    static List<UiListItem> from(final Category category) {
        //TODO
        return null;
    }
}
