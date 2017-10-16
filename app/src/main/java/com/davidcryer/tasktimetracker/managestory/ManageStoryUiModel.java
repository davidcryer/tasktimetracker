package com.davidcryer.tasktimetracker.managestory;

import com.davidc.uiwrapper.UiModel;

public interface ManageStoryUiModel extends UiModel<ManageStoryUi> {
    void showManageLayout(ManageStoryUi ui);
    void showManageLayout(ManageStoryUi ui, UiStory story);
    void showEditLayout(ManageStoryUi ui);
    void showManageTask(ManageStoryUi ui, UiTask task, int i);
    void showEditTask(ManageStoryUi ui, UiTask task, int i);
    void showSaveError(ManageStoryUi ui, String title, String message);
    void onDismissSaveError();
}
