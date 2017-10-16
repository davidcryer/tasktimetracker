package com.davidcryer.tasktimetracker.managestories;

import com.davidcryer.tasktimetracker.common.domain.Story;
import com.davidcryer.tasktimetracker.common.domain.Task;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UiTaskMapper {

    private UiTaskMapper() {}

    static List<UiTask> from(final Story story) {
        final List<UiTask> uiTasks = new ArrayList<>(story.tasks().size());
        for (final Task task : story.tasks()) {
            uiTasks.add(new UiTask(task.id(), task.title(), task.note()));
        }
        return uiTasks;
    }

    static ManageTaskIntentModel toManageTaskIntentModel(final UiTask task, final UUID storyId) {
        return new ManageTaskIntentModel(task.getId(), task.getTitle(), task.getNote(), storyId);
    }
}
