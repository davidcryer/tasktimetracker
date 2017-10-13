package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.StorySharedPreferences;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUi;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUiModel;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUiModelFactory;
import com.davidcryer.tasktimetracker.managetasks.ManageTasksUiWrapper;

public class UiWrapperFactory {
    private final StoryDatabase storyDatabase;

    public UiWrapperFactory(StoryDatabase storyDatabase) {
        this.storyDatabase = storyDatabase;
    }

    UiWrapper<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel> createManageTasksUiWrapper(final Bundle savedInstanceState) {
        return savedInstanceState == null
                ? ManageTasksUiWrapper.newInstance(new ManageTasksUiModelFactory(), storyDatabase)
                : ManageTasksUiWrapper.savedElseNewInstance(new ManageTasksUiModelFactory(), savedInstanceState, storyDatabase);
    }
}
