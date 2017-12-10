package com.davidcryer.tasktimetracker.managecategories;

import com.davidcryer.tasktimetracker.common.domain.Category;
import com.davidcryer.tasktimetracker.common.domain.Task;
import com.davidcryer.tasktimetracker.managetask.ManageTaskIntentModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UiTaskMapper {

    private UiTaskMapper() {}

    static List<UiTask> from(final Category category) {
        final List<UiTask> uiTasks = new ArrayList<>(category.tasks().size());
        for (final Task task : category.tasks()) {
            uiTasks.add(from(task, category));
        }
        return uiTasks;
    }

    static UiTask from(final Task task, final Category category) {
        return new UiTask(task.id(), task.title(), task.note(), task.expendedTime(), task.isOngoing(), category.id());
    }

    static ManageTaskIntentModel toManageTaskIntentModel(final UiTask task) {
        return new ManageTaskIntentModel(task.getId(), task.getTitle(), task.getNote(), task.getCategoryId());
    }
}
