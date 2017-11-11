package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.CategoryDatabase;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModel;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModelFactory;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiWrapper;

public class UiWrapperFactory {
    private final CategoryDatabase categoryDatabase;

    public UiWrapperFactory(CategoryDatabase categoryDatabase) {
        this.categoryDatabase = categoryDatabase;
    }

    UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> createManageCategoriesUiWrapper(final Bundle savedInstanceState) {
        return savedInstanceState == null
                ? ManageCategoriesUiWrapper.newInstance(new ManageCategoriesUiModelFactory(), categoryDatabase)
                : ManageCategoriesUiWrapper.savedElseNewInstance(savedInstanceState, new ManageCategoriesUiModelFactory(), categoryDatabase);
    }
}
