package com.davidcryer.tasktimetracker.managestories;

import com.davidc.uiwrapper.UiModel;

import java.util.List;

public interface ManageStoriesUiModel extends UiModel<ManageStoriesUi> {
    void showStories(List<UiStory> stories, ManageStoriesUi ui);
    boolean isPopulated();
}
