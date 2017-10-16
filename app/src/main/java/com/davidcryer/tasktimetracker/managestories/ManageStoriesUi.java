package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managestory.ManageStoryIntentModel;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;

public interface ManageStoriesUi {
    void showStories(List<UiStory> stories);
    void addStory(UiStory story);
    void insertStory(UiStory story, int i);
    void removeStory(int i);
    void showRemoveStory(UiStory story, int i);
    void showUndoStoryRemoval(Runnable onUndo, Runnable onDismiss);
    void showManageStoryScreen(ManageStoryIntentModel intentModel);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddStoryScreen();

    interface Listener {
        void onClickStory(ManageStoriesUi ui, UiStory story);
        void onLongClickStory(ManageStoriesUi ui, UiStory story, int i);
        void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story);
        void onClickAddStory(ManageStoriesUi ui);
        void onClickRemoveStory(ManageStoriesUi ui, UiStory story, int i);
        void onAddStoryResult(ManageStoriesUi ui, UiStory story);
    }
}
