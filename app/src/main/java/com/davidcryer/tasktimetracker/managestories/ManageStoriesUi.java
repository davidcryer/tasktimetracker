package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.List;
import java.util.UUID;

public interface ManageStoriesUi {
    void showStories(List<UiStory> stories);
    void addStory(UiStory story);
    void insertStory(UiStory story, int i);
    void setStory(UiStory story, int i);
    void removeStory(int i);
    void expandStory(int i, int pos);
    void shrinkStory(int i, int pos);
    void showAddStoryPrompt();
    void showRemoveStoryPrompt(UiStory story, int i);
    void showManageTaskScreen(ManageTaskIntentModel intentModel);

    interface InputStoryPrompt {
        void showTitleError(String message);
        void showNoteError(String message);
        void dismiss();
    }

    interface Listener {
        void onClickStory(ManageStoriesUi ui, UiStory story, int i);
        void onLongClickStory(ManageStoriesUi ui, UiStory story, int i);
        void onClickTask(ManageStoriesUi ui, UiTask task, UiStory story);
        void onClickAddStory(ManageStoriesUi ui);
        void onClickAddTask(ManageStoriesUi ui, UiStory story);
        void onAddStory(InputStoryPrompt prompt, String title, String note);
        void onEditStory(InputStoryPrompt prompt, UUID storyId, String title, String note);
        void onRemoveStory(ManageStoriesUi ui, UiStory story, int i);
    }
}
