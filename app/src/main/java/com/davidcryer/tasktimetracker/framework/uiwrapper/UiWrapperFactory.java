package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUi;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUiModel;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUiModelFactory;
import com.davidcryer.tasktimetracker.currenttasks.CurrentTasksUiWrapper;

public class UiWrapperFactory {

    UiWrapper<CurrentTasksUi, CurrentTasksUi.Listener, CurrentTasksUiModel> createCurrentTasksUiWrapper(
            final Bundle savedInstanceState
    ) {
        return savedInstanceState == null
                ? CurrentTasksUiWrapper.newInstance(new CurrentTasksUiModelFactory())
                : CurrentTasksUiWrapper.savedElseNewInstance(new CurrentTasksUiModelFactory(), savedInstanceState);
    }
}
