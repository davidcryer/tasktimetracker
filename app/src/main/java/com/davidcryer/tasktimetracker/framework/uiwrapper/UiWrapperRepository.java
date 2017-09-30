package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiBinder;
import com.davidc.uiwrapper.UiUnbinder;
import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUi;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUiModel;

import java.util.HashMap;
import java.util.Map;

public class UiWrapperRepository {
    private final UiWrapperFactory uiWrapperFactory;
    private final Map<String, UiWrapper<TaskHistoryUi, TaskHistoryUi.Listener, TaskHistoryUiModel>> taskHistoryUiWrapperMap = new HashMap<>();

    public UiWrapperRepository(final UiWrapperFactory uiWrapperFactory) {
        this.uiWrapperFactory = uiWrapperFactory;
    }

    public TaskHistoryUi.Listener bind(final TaskHistoryUi ui, final UiBinder binder) {
        return binder.bind(ui, taskHistoryUiWrapperMap, new UiBinder.UiWrapperProvider<TaskHistoryUi, TaskHistoryUi.Listener, TaskHistoryUiModel>() {
            @NonNull
            @Override
            public UiWrapper<TaskHistoryUi, TaskHistoryUi.Listener, TaskHistoryUiModel> uiWrapper(Bundle savedInstanceState) {
                return uiWrapperFactory.createTaskHistoryUiWrapper(savedInstanceState);
            }
        });
    }

    public void unbind(final TaskHistoryUi ui, final UiUnbinder unbinder) {
        unbinder.unbind(taskHistoryUiWrapperMap);
    }
}
