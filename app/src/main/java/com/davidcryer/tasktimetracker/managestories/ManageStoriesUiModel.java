package com.davidcryer.tasktimetracker.managestories;

import com.davidc.uiwrapper.UiModel;

import java.util.List;
import java.util.UUID;

public interface ManageStoriesUiModel extends UiModel<ManageStoriesUi> {
    void showStories(List<UiStory> stories, ManageStoriesUi ui);
    void removeStory(UUID storyId, ManageStoriesUi ui);
    void removeTask(UUID taskId, UUID storyId, ManageStoriesUi ui);
    void addStory(UiStory story, ManageStoriesUi ui);
    void insertStory(UiStory story, int i, ManageStoriesUi ui);
    void updateStory(UiStory story, ManageStoriesUi ui);
    void expandStory(UiStory story, int pos, ManageStoriesUi ui);
    void shrinkStory(UiStory story, int pos, ManageStoriesUi ui);
    boolean isPopulated();
}
