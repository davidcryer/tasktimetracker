package com.davidcryer.tasktimetracker.managetasks;

import java.util.List;

public interface ManageTasksUi {
    void showStories(List<UiStory> stories);

    interface Listener {

    }
}
