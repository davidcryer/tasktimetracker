package com.davidcryer.tasktimetracker.managetasks;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;

public class ManageTasksUiWrapper extends UiWrapper<ManageTasksUi, ManageTasksUi.Listener, ManageTasksUiModel> {
    private final StoryDatabase storyDatabase;

    private ManageTasksUiWrapper(@NonNull final ManageTasksUiModel uiModel, final StoryDatabase storyDatabase) {
        super(uiModel);
        this.storyDatabase = storyDatabase;
    }

    public static ManageTasksUiWrapper newInstance(final ManageTasksUiModelFactory modelFactory, final StoryDatabase storyDatabase) {
        return new ManageTasksUiWrapper(modelFactory.create(), storyDatabase);
    }

    public static ManageTasksUiWrapper savedElseNewInstance(
            final ManageTasksUiModelFactory modelFactory,
            final Bundle savedInstanceState,
            final StoryDatabase storyDatabase
    ) {
        final ManageTasksUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory, storyDatabase) : new ManageTasksUiWrapper(savedModel, storyDatabase);
    }

    @Override
    protected ManageTasksUi.Listener uiListener() {
        return new ManageTasksUi.Listener() {

        };
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulated()) {
            uiModel().showStories(UiStoryMapper.from(storyDatabase.findAll()), ui());
        }
    }
}
