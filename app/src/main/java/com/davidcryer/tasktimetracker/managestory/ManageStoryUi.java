package com.davidcryer.tasktimetracker.managestory;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public interface ManageStoryUi {
    void showTasks(List<UiTask> tasks);
    void showManageLayout(UiStory story);
    void showEditLayout(UiStory story);
    void showManageTask(UiTask task, int i);
    void showEditTask(UiTask task, int i);
    void showSaveError(String title, String message);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);

    interface Listener {
        void onClickEdit(ManageStoryUi ui);
        void onClickCancelEdit(ManageStoryUi ui);
        void onClickSaveStory(ManageStoryUi ui, UiStory story);
        void onClickTask(ManageStoryUi ui, UiTask task);
        void onClickEditTask(ManageStoryUi ui, int i);
        void onClickCancelTaskEdit(ManageStoryUi ui, int i);
        void onClickSaveTask(ManageStoryUi ui, UiTask task, int i);
        void onDismissSaveError();
    }
}
