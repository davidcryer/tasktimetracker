package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel>> manageStoriesUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public ManageStoriesUi.Listener bind(final ManageStoriesUi ui, final UiBinder binder) {
        return binder.bind(ui, manageStoriesUiWrapperMap, new UiBinder.UiWrapperProvider<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel>() {
            @NonNull
            @Override
            public UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createManageStoriesUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final ManageStoriesUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(manageStoriesUiWrapperMap);
    }
}
