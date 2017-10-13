package com.davidcryer.tasktimetracker.managetasks;

import com.davidcryer.tasktimetracker.common.domain.Task;

import java.util.ArrayList;
import java.util.List;

class UiTaskMapper {

    private UiTaskMapper() {}

    static List<UiTask> from(final List<Task> tasks) {
        final List<UiTask> uiTasks = new ArrayList<>(tasks.size());
        for (final Task task : tasks) {
            uiTasks.add(new UiTask(task.id(), task.title()));
        }
        return uiTasks;
    }
}
