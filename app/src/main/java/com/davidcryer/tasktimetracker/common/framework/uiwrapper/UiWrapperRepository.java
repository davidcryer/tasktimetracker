package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModel;
import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUi;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel>> manageTasksUiWrapperMap = new HashMap<>();
    private final Map<String, UiWrapper<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel>> manageStoryUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public ManageStoriesUi.Listener bind(final ManageStoriesUi ui, final UiBinder binder) {
        return binder.bind(ui, manageTasksUiWrapperMap, new UiBinder.UiWrapperProvider<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel>() {
            @NonNull
            @Override
            public UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createManageStoriesUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final ManageStoriesUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(manageTasksUiWrapperMap);
    }

    public ManageStoryUi.Listener bind(final ManageStoryUi ui, final UiBinder binder, final ManageStoryIntentModel intentModel) {
        return binder.bind(ui, manageStoryUiWrapperMap, new UiBinder.UiWrapperProvider<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel>() {
            @NonNull
            @Override
            public UiWrapper<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createManageStoryUiWrapper(savedInstanceState, intentModel);
            }
        });
    }

    public void unbind(final ManageStoryUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(manageStoryUiWrapperMap);
    }
}
