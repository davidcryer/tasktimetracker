package com.davidcryer.tasktimetracker.managestory;

import com.davidcryer.tasktimetracker.common.domain.Task;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class UiTaskMapper {

    private UiTaskMapper() {}

    public static List<UiTask> from(final List<Task> tasks) {
        final List<UiTask> uiTasks = new LinkedList<>();
        for (final Task task : tasks) {
            uiTasks.add(new UiTask(task.id(), task.title(), task.note(), false));
        }
        return uiTasks;
    }

    public static ManageTaskIntentModel toManageTaskIntentModel(final UiTask task, final UUID storyId) {
        return new ManageTaskIntentModel(task.getId(), task.getTitle(), task.getNote(), storyId);
    }
}
