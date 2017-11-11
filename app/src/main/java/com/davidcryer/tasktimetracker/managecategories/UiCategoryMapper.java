package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.domain.Category;

import java.util.ArrayList;
import java.util.List;

class UiCategoryMapper {

    private UiCategoryMapper() {}

    static List<UiCategory> from(final List<Category> categories) {
        final List<UiCategory> uiCategories = new ArrayList<>(categories.size());
        for (final Category category : categories) {
            uiCategories.add(from(category));
        }
        return uiCategories;
    }

    static UiCategory from(final Category category) {
        return new UiCategory(category.id(), category.title(), category.note(), false, UiTaskMapper.from(category));
    }
}
