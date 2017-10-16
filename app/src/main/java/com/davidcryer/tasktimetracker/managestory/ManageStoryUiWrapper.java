package com.davidcryer.tasktimetracker.managestory;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.davidc.uiwrapper.UiWrapper;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.common.domain.StoryDatabase;
import com.davidcryer.tasktimetracker.common.domain.Task;

public class ManageStoryUiWrapper extends UiWrapper<ManageStoryUi, ManageStoryUi.Listener, ManageStoryUiModel> {
    private final static String SAVE_ERROR_TITLE = "Save error";
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
                    final Story domainStory = storyDatabase.find(uiModel().storyId());
                    domainStory.writer().title(story.getTitle()).note(story.getNote()).commit();
                    storyDatabase.save(domainStory);
                    uiModel().showManageLayout(ui, story);
                } catch (IllegalArgsException iae) {
                    showSaveError(ui, iae);
                }
            }

            @Override
            public void onClickTask(ManageStoryUi ui, UiTask task) {
                ui.showManageTaskScreen(UiTaskMapper.toManageTaskIntentModel(task, uiModel().storyId()));
            }

            @Override
            public void onClickEditTask(ManageStoryUi ui, int i) {
                uiModel().showEditTask(ui, i);
            }

            @Override
            public void onClickCancelTaskEdit(ManageStoryUi ui, int i) {
                uiModel().showManageTask(ui, i);
            }

            @Override
            public void onClickSaveTask(ManageStoryUi ui, UiTask task, int i) {
                try {
                    final Story domainStory = storyDatabase.find(uiModel().storyId());
                    domainStory.tasks().add(new Task(task.getTitle(), task.getNote()));
                    storyDatabase.save(domainStory);
                    uiModel().showManageTask(ui, task, i);
                } catch (IllegalArgsException iae) {
                    showSaveError(ui, iae);
                }
            }

            private void showSaveError(ManageStoryUi ui, IllegalArgsException iae) {
                uiModel().showSaveError(ui, SAVE_ERROR_TITLE, iae.getMessage());
            }
        };
    }

    @Override
    protected void registerResources() {
        super.registerResources();
        if (!uiModel().isPopulatedWithTasks()) {
            final Story story = storyDatabase.find(uiModel().storyId());
            uiModel().showTasks(ui(), UiTaskMapper.from(story.tasks()));
        }
    }
}
