package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUi;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<CurrentTasksUi, CurrentTasksUi.Listener, CurrentTasksUiModel>> currentTasksUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public CurrentTasksUi.Listener bind(final CurrentTasksUi ui, final UiBinder binder) {
        return binder.bind(ui, currentTasksUiWrapperMap, new UiBinder.UiWrapperProvider<CurrentTasksUi, CurrentTasksUi.Listener, CurrentTasksUiModel>() {
            @NonNull
            @Override
            public UiWrapper<CurrentTasksUi, CurrentTasksUi.Listener, CurrentTasksUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createCurrentTasksUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final CurrentTasksUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(currentTasksUiWrapperMap);
    }
}
