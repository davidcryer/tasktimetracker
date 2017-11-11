package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.managestories.ManageCategoriesUi;
import com.davidcryer.tasktimetracker.managestories.ManageCategoriesUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel>> manageCategoriesUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public ManageCategoriesUi.Listener bind(final ManageCategoriesUi ui, final UiBinder binder) {
        return binder.bind(ui, manageCategoriesUiWrapperMap, new UiBinder.UiWrapperProvider<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel>() {
            @NonNull
            @Override
            public UiWrapper<ManageCategoriesUi, ManageCategoriesUi.Listener, ManageCategoriesUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createManageCategoriesUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final ManageCategoriesUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(manageCategoriesUiWrapperMap);
    }
}
