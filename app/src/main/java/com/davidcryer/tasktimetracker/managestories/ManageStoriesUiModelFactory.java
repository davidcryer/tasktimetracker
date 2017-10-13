package com.davidcryer.tasktimetracker.managestories;

import java.util.List;

public class ManageStoriesUiModelFactory {
    private final static List<UiStory> DEFAULT_STORIES = null;

    ManageStoriesUiModel create() {
        return new ManageStoriesUiModelImpl(DEFAULT_STORIES);
    }
}
