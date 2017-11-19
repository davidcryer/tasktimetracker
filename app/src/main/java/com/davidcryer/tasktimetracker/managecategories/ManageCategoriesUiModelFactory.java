package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.domain.Category;

import java.util.Date;
import java.util.List;

public class ManageCategoriesUiModelFactory {
    private final static List<Category> DEFAULT_CATEGORIES = null;
    private final static Date DEFAULT_CATEGORIES_SET = null;
    private final static Integer DEFAULT_FILTERED_CATEGORY = null;

    ManageCategoriesUiModel create() {
        return new ManageCategoriesUiModelImpl(DEFAULT_CATEGORIES, DEFAULT_CATEGORIES_SET, DEFAULT_FILTERED_CATEGORY);
    }
}
