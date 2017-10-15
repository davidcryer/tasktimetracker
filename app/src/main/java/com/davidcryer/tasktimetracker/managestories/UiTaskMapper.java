package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.ArrayList;
import java.util.List;

class UiTaskMapper {

    private UiTaskMapper() {}

    static List<UiTask> from(final Story story) {
        final List<UiTask> uiTasks = new ArrayList<>(story.tasks().size());
        for (final Task task : story.tasks()) {
            uiTasks.add(new UiTask(task.id(), task.title(), story.id()));
        }
        return uiTasks;
    }
}
