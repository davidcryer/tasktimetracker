package com.davidcryer.tasktimetracker.managestories;

import java.util.List;

public interface ManageStoriesUi {
    void showStories(List<UiStory> stories);

    interface Listener {

    }
}
