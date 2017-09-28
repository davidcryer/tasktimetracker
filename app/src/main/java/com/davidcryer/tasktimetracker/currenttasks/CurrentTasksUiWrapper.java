package com.davidcryer.tasktimetracker.currenttasks;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;


public class CurrentTasksUiWrapper extends UiWrapper<CurrentTasksUi, CurrentTasksUi.Listener, CurrentTasksUiModel> {

    private CurrentTasksUiWrapper(@NonNull final CurrentTasksUiModel uiModel) {
        super(uiModel);
    }

    public static CurrentTasksUiWrapper newInstance(final CurrentTasksUiModelFactory modelFactory) {
        return new CurrentTasksUiWrapper(modelFactory.create());
    }

    public static CurrentTasksUiWrapper savedElseNewInstance(final CurrentTasksUiModelFactory modelFactory, final Bundle savedInstanceState) {
        final CurrentTasksUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory) : new CurrentTasksUiWrapper(savedModel);
    }

    @Override
    protected CurrentTasksUi.Listener uiListener() {
        return null;
    }
}
