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
    void showManageTaskScreen(ManageTaskIntentModel intentModel);
    void showAddStoryScreen();

    interface Listener {
        void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story);
        void onClickAddStory(ManageStoriesUi ui);
        void onAddStoryResult(ManageStoriesUi ui, UiStory story);
    }
}
