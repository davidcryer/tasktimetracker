package com.davidcryer.tasktimetracker.managestory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;

public class ManageStoryUiWrapper extends UiWrapper<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel> {
    private final StoryDatabase storyDatabase;

    private ManageStoryUiWrapper(@NonNull ManageStoryUiModel uiModel, StoryDatabase storyDatabase) {
        super(uiModel);
        this.storyDatabase = storyDatabase;
    }

    public static ManageStoryUiWrapper newInstance(final ManageStoryUiModelFactory modelFactory, final StoryDatabase storyDatabase) {
        return new ManageStoryUiWrapper(modelFactory.create(), storyDatabase);
    }

    public static ManageStoryUiWrapper savedElseNewInstance(
            @NonNull final Bundle savedInstance,
            final ManageStoryUiModelFactory modelFactory,
            final StoryDatabase storyDatabase
    ) {
        final ManageStoryUiModel model = savedUiModel(savedInstance);
        return model == null ? newInstance(modelFactory, storyDatabase) : new ManageStoryUiWrapper(model, storyDatabase);
    }

    @Override
    protected ManageStoryUi.Listener uiListener() {
        return new ManageStoryUi.Listener() {
            @Override
            public void onClickEdit(ManageStoryUi ui) {
                uiModel().showEditLayout(ui);
            }

            @Override
            public void onClickCancelEdit(ManageStoryUi ui) {
                uiModel().showManageLayout(ui);
            }

            @Override
            public void onClickSaveStory(ManageStoryUi ui, UiStory story) {
                try {
                    final Story domainStory = storyDatabase.find(story.getId());
                    domainStory.writer().title(story.getTitle()).note(story.getNote()).commit();
                    storyDatabase.save(domainStory);
                    uiModel().showManageLayout(ui, story);
                } catch (IllegalArgsException iae) {
                    ui.showSaveError("Save error", iae.getMessage());
                }
            }

            @Override
            public void onClickEditTask(ManageStoryUi ui, int i) {

            }

            @Override
            public void onClickCancelTaskEdit(ManageStoryUi ui, int i) {

            }

            @Override
            public void onClickSaveTask(ManageStoryUi ui, UiTask task, int i) {

            }
        };
    }
}
