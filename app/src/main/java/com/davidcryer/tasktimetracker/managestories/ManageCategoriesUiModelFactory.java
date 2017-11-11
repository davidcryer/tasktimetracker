package com.davidcryer.tasktimetracker.managestories;

import java.util.List;

public class ManageCategoriesUiModelFactory {
    private final static List<UiCategory> DEFAULT_CATEGORIES = null;

    ManageCategoriesUiModel create() {
        return new ManageCategoriesUiModelImpl(DEFAULT_CATEGORIES);
    }
}
