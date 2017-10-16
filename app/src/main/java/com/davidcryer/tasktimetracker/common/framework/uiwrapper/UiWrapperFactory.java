package com.davidcryer.tasktimetracker.common.framework.uiwrapper;

import android.os.Bundle;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUi;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModel;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiModelFactory;
import com.davidcryer.tasktimetracker.managestories.ManageStoriesUiWrapper;
import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUi;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUiModel;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUiModelFactory;
import com.davidcryer.tasktimetracker.managestory.ManageStoryUiWrapper;

public class UiWrapperFactory {
    private final StoryDatabase storyDatabase;

    public UiWrapperFactory(StoryDatabase storyDatabase) {
        this.storyDatabase = storyDatabase;
    }

    UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> createManageStoriesUiWrapper(final Bundle savedInstanceState) {
        return savedInstanceState == null
                ? ManageStoriesUiWrapper.newInstance(new ManageStoriesUiModelFactory(), storyDatabase)
                : ManageStoriesUiWrapper.savedElseNewInstance(savedInstanceState, new ManageStoriesUiModelFactory(), storyDatabase);
    }

    UiWrapper<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel> createManageStoryUiWrapper(final Bundle savedInstanceState, final ManageStoryIntentModel intentModel) {
        return savedInstanceState == null
                ? ManageStoryUiWrapper.newInstance(new ManageStoryUiModelFactory(intentModel), storyDatabase)
                : ManageStoryUiWrapper.savedElseNewInstance(savedInstanceState, new ManageStoryUiModelFactory(intentModel), storyDatabase);
    }
}
