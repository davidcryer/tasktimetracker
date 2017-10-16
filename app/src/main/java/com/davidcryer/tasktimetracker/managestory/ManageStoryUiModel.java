package com.davidcryer.tasktimetracker.managestory;

import com.davidc.uiwrapper.UiModel;

import java.util.List;
import java.util.UUID;

public interface ManageStoryUiModel extends UiModel<ManageStoryUi> {
    boolean isPopulatedWithTasks();
    void showTasks(ManageStoryUi ui, List<UiTask> tasks);
    void showManageLayout(ManageStoryUi ui);
    void showManageLayout(ManageStoryUi ui, UiStory story);
    void showEditLayout(ManageStoryUi ui);
    void showManageTask(ManageStoryUi ui, int i);
    void showManageTask(ManageStoryUi ui, UiTask task, int i);
    void showEditTask(ManageStoryUi ui, int i);
    void showSaveError(ManageStoryUi ui, String title, String message);
    void onDismissSaveError();
    UUID storyId();
}
