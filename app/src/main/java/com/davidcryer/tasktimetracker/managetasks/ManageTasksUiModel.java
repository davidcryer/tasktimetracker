package com.davidcryer.tasktimetracker.managetasks;

import com.davidc.uiwrapper.UiModel;

import java.util.List;

public interface ManageTasksUiModel extends UiModel<ManageTasksUi> {
    void showStories(List<UiStory> stories, ManageTasksUi ui);
    boolean isPopulated();
}
