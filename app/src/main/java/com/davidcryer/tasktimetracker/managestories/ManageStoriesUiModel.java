package com.davidcryer.tasktimetracker.managestories;

import com.davidc.uiwrapper.UiModel;

import java.util.List;

public interface ManageStoriesUiModel extends UiModel<ManageStoriesUi> {
    void showStories(List<UiStory> stories, ManageStoriesUi ui);
    void removeStory(int i, ManageStoriesUi ui);
    void addStory(UiStory story, ManageStoriesUi ui);
    void insertStory(UiStory story, int i, ManageStoriesUi ui);
    boolean isPopulated();
}
