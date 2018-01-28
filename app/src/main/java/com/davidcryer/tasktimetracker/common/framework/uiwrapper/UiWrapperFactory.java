package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidc.uiwrapper.UiWrapperInitializer;
import com.davidcryer.tasktimetracker.common.domain.DomainManager;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModel;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiModelFactory;
import com.davidcryer.tasktimetracker.managecategories.ManageCategoriesUiWrapper;

public class UiWrapperFactory {
    private final DomainManager domainManager;

    public UiWrapperFactory(DomainManager domainManager) {
        this.domainManager = domainManager;
    }

    public UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> createManageCategoriesUiWrapper(final Bundle savedState) {
        return UiWrapperInitializer.from(savedState,
                () -> ManageCategoriesUiWrapper.newInstance(new ManageCategoriesUiModelFactory(), domainManager),
                nonNullSavedState -> ManageCategoriesUiWrapper.savedElseNewInstance(savedState, new ManageCategoriesUiModelFactory(), domainManager)
        );
    }
}
