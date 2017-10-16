package com.davidcryer.tasktimetracker.managestory;

public interface ManageStoryUi {
    void showManageLayout(UiStory story);
    void showEditLayout(UiStory story);
    void showManageTask(UiTask task, int i);
    void showEditTask(UiTask task, int i);
    void showSaveError(String title, String message);

    interface Listener {
        void onClickEdit(ManageStoryUi ui);
        void onClickCancelEdit(ManageStoryUi ui);
        void onClickSaveStory(ManageStoryUi ui, UiStory story);
        void onClickEditTask(ManageStoryUi ui, int i);
        void onClickCancelTaskEdit(ManageStoryUi ui, int i);
        void onClickSaveTask(ManageStoryUi ui, UiTask task, int i);
    }
}
