package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public interface ManageStoriesUi {
    void showStories(List<UiStory> stories);
    void addStory(UiStory story);
    void insertStory(UiStory story, int i);
    void removeStory(int i);
    void showStoryTasks(int i);
    void hideStoryTasks(int i);
//    void showEditStoryPrompt(UiStoryEdit story, int i);
//    void showUndoStoryRemovalSnackbar(Runnable onUndo, Runnable onDismiss);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddStoryScreen();

    interface Listener {
        void onClickStory(ManageStoriesUi ui, UiStory story);
        void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story);
//        void onClickEditStory(ManageStoriesUi ui, UiStory story);
//        void onClickCancelStoryEdit(ManageStoriesUi ui);
//        void onClickSaveStoryEdit(ManageStoriesUi ui, UiStoryEdit story, int i);
//        void onClickRemoveStory(ManageStoriesUi ui, int i);
        void onClickAddStory(ManageStoriesUi ui);
        void onAddStoryResult(ManageStoriesUi ui, UiStory story);
    }
}
