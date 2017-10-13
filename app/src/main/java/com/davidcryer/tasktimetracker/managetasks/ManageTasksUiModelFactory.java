package com.davidcryer.tasktimetracker.managetasks;

import java.util.List;

public class ManageTasksUiModelFactory {
    private final static List<UiStory> DEFAULT_STORIES = null;

    ManageTasksUiModel create() {
        return new ManageTasksUiModelImpl(DEFAULT_STORIES);
    }
}
