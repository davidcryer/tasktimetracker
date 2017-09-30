package com.davidcryer.tasktimetracker.taskhistory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;


public class TaskHistoryUiWrapper extends UiWrapper<TaskHistoryUi, TaskHistoryUi.Listener, TaskHistoryUiModel> {

    private TaskHistoryUiWrapper(@NonNull final TaskHistoryUiModel uiModel) {
        super(uiModel);
    }

    public static TaskHistoryUiWrapper newInstance(final TaskHistoryUiModelFactory modelFactory) {
        return new TaskHistoryUiWrapper(modelFactory.create());
    }

    public static TaskHistoryUiWrapper savedElseNewInstance(final TaskHistoryUiModelFactory modelFactory, final Bundle savedInstanceState) {
        final TaskHistoryUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory) : new TaskHistoryUiWrapper(savedModel);
    }

    @Override
    protected TaskHistoryUi.Listener uiListener() {
        return new TaskHistoryUi.Listener() {

        };
    }
}
