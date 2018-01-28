package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

class CategoryFactory {
    private final TaskFactory taskFactory;

    CategoryFactory(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    Category create(final CategoryStore categoryStore, final String title, final String note) throws CategoryArgRules.Exception {
        return Category.create(categoryStore, taskFactory, title, note);
    }

    Category inflate(final CategoryStore categoryStore, final UUID id, final String title, final String note, final List<DbTask> tasks, final Task.OngoingStatusListener ongoingStatusListener) throws CategoryArgRules.Exception {
        return Category.inflate(categoryStore, taskFactory, id, title, note, DbMapper.tasks(tasks, taskFactory, ongoingStatusListener));
    }
}
