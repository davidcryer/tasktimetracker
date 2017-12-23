package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.CategoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.TaskFactory;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModel;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModelFactory;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiWrapper;

public class UiWrapperFactory {
    private final CategoryDatabase categoryDatabase;
    private final TaskFactory taskFactory;

    public UiWrapperFactory(CategoryDatabase categoryDatabase, TaskFactory taskFactory) {
        this.categoryDatabase = categoryDatabase;
        this.taskFactory = taskFactory;
    }

    public UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> createManageCategoriesUiWrapper(final Bundle savedState) {
        return savedState == null
                ? ManageCategoriesUiWrapper.newInstance(new ManageCategoriesUiModelFactory(), categoryDatabase, taskFactory)
                : ManageCategoriesUiWrapper.savedElseNewInstance(savedState, new ManageCategoriesUiModelFactory(), categoryDatabase, taskFactory);
    }
}
