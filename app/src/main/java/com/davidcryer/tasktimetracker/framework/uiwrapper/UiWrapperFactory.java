package com.davidcryer.tasktimetracker.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModel;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModelFactory;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiWrapper;

public class UiWrapperFactory {
    private final StoryDatabase storyDatabase;

    public UiWrapperFactory(StoryDatabase storyDatabase) {
        this.storyDatabase = storyDatabase;
    }

    UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> createManageStoriesUiWrapper(final Bundle savedInstanceState) {
        return savedInstanceState == null
                ? ManageStoriesUiWrapper.newInstance(new ManageStoriesUiModelFactory(), storyDatabase)
                : ManageStoriesUiWrapper.savedElseNewInstance(new ManageStoriesUiModelFactory(), savedInstanceState, storyDatabase);
    }
}
