package com.davidcryer.tasktimetracker.managestories;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;

public class ManageStoriesUiWrapper extends UiWrapper<ManageStoriesUi, ManageStoriesUi.Listener, ManageStoriesUiModel> {
    private final StoryDatabase storyDatabase;

    private ManageStoriesUiWrapper(@NonNull final ManageStoriesUiModel uiModel, final StoryDatabase storyDatabase) {
        super(uiModel);
        this.storyDatabase = storyDatabase;
    }

    public static ManageStoriesUiWrapper newInstance(final ManageStoriesUiModelFactory modelFactory, final StoryDatabase storyDatabase) {
        return new ManageStoriesUiWrapper(modelFactory.create(), storyDatabase);
    }

    public static ManageStoriesUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstanceState,
            final ManageStoriesUiModelFactory modelFactory,
            final StoryDatabase storyDatabase
    ) {
        final ManageStoriesUiModel savedModel = savedUiModel(savedInstanceState);
        return savedModel == null ? newInstance(modelFactory, storyDatabase) : new ManageStoriesUiWrapper(savedModel, storyDatabase);
    }

    @Override
    protected ManageStoriesUi.Listener uiListener() {
        return new ManageStoriesUi.Listener() {
            @Override
            public void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story) {
                ui.showManageTaskScreen(UiTaskMapper.toManageTaskIntentModel(task, story.getId()));
            }

            @Override
            public void onClickAddStory(ManageStoriesUi ui) {
                ui.showAddStoryScreen();
            }

            @Override
            public void onAddStoryResult(ManageStoriesUi ui, UiStory story) {
                uiModel().addStory(story, ui);
            }
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
