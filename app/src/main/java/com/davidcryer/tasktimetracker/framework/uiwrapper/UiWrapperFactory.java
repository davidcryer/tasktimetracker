package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUi;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUiModel;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUiModelFactory;
import com.davidcryer.tasktimetracker.taskhistory.TaskHistoryUiWrapper;

public class UiWrapperFactory {

    UiWrapper<TaskHistoryUi, TaskHistoryUi.Listener, TaskHistoryUiModel> createTaskHistoryUiWrapper(
            final Bundle savedInstanceState
    ) {
        return savedInstanceState == null
                ? TaskHistoryUiWrapper.newInstance(new TaskHistoryUiModelFactory())
                : TaskHistoryUiWrapper.savedElseNewInstance(new TaskHistoryUiModelFactory(), savedInstanceState);
    }
}
