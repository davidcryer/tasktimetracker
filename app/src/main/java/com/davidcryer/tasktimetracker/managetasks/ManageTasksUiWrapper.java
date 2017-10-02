package com.davidcryer.tasktimetracker.managetasks;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;

public class ManageTasksUiWrapper extends UiWrapper<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel> {

    private ManageTasksUiWrapper(@NonNull final ManageTasksUiModel uiModel) {
        super(uiModel);
    }

    public static ManageTasksUiWrapper newInstance(final ManageTasksUiModelFactory modelFactory) {
        return new ManageTasksUiWrapper(modelFactory.create());
    }

    public static ManageTasksUiWrapper savedElseNewInstance(final ManageTasksUiModelFactory modelFactory, final Bundle savedInstanceState) {
        final ManageTasksUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory) : new ManageTasksUiWrapper(savedModel);
    }

    @Override
    protected ManageTasksUi.Listener uiListener() {
        return new ManageTasksUi.Listener() {

        };
    }
}
