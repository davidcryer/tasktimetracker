package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ObjectUtils;
import com.davidcryer.tasktimetracker.common.domain.exceptions.CategoryArgResults;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Category implements Task.OnChangeListener {
    private final CategoryStore categoryStore;
    private final TaskFactory taskFactory;
    private final UUID id;
    private String title;
    private String note;
    private List<ObservedTask> tasks;

    private Category(CategoryStore categoryStore, TaskFactory taskFactory, UUID id, String title, String note, List<ObservedTask> tasks) throws CategoryArgResults.Exception {
        new CategoryArgChecker().id(id).title(title).check();
        this.categoryStore = categoryStore;
        this.taskFactory = taskFactory;
        this.id = id;
        this.title = title;
        this.note = note;
        this.tasks = tasks;
    }

    static Category create(CategoryStore categoryStore, TaskFactory taskFactory, String title, String note) throws CategoryArgResults.Exception {
        final Category category = new Category(categoryStore, taskFactory, UUID.randomUUID(), title, note, null);
        categoryStore.save(category);
        return category;
    }

    static Category inflate(CategoryStore categoryStore, TaskFactory taskFactory, UUID id, String title, String note, List<ObservedTask> tasks) throws CategoryArgResults.Exception {
        final Category category = new Category(categoryStore, taskFactory, id, title, note, tasks);
        category.listenToTaskChanges();
        return category;
    }

    private void listenToTaskChanges() {
        if (tasks != null) {
            tasks.forEach(task -> task.onChangeListener(this));
        }
    }

    public UUID id() {
        return id;
    }

    public String title() {
        return title;
    }

    private void title(final String title) {
        this.title = title;
    }

    public String note() {
        return note;
    }

    private void note(final String note) {
        this.note = note;
    }

    public List<ObservedTask> tasks() {
        return tasks == null ? new ArrayList<>() : new ArrayList<>(tasks);
    }

    public ObservedTask newTask(final String title, final String note) {
        if (tasks == null) {
            tasks = new LinkedList<>();
        }
        final ObservedTask task = taskFactory.create(title, note);
        tasks.add(task);
        task.onChangeListener(this);
        save();
        return task;
    }

    public boolean deleteTask(final UUID taskId) {
        if (tasks != null) {
            for (final Iterator<ObservedTask> itr = tasks.iterator(); itr.hasNext(); ) {
                final Task task = itr.next();
                if (task.id().equals(taskId)) {
                    task.onChangeListener(null);
                    if (task.isActive()) {
                        task.deactivate();
                    }
                    itr.remove();
                    save();
                    return true;
                }
            }
        }
        return false;
    }

    public long expendedTime() {
        long expendedTime = 0L;
        if (tasks != null) {
            for (final Task task : tasks) {
                expendedTime += task.expendedTime();
            }
        }
        return expendedTime;
    }

    @Override
    public void taskChanged(Task task) {
        save();
    }

    private void save() {
        categoryStore.save(this);
    }

    public void delete() {
        categoryStore.delete(this);
    }

    DbCategory toDbCategory() {
        return new DbCategory(id, title, note, DbMapper.dbTasks(tasks.toArray(new ObservedTask[tasks.size()])));
    }

    public Writer writer() {
        return new Writer(this);
    }

    public static class Writer {
        private final Category category;
        private String title;
        private boolean titleChanged;
        private String note;
        private boolean noteChanged;

        private Writer(Category category) {
            this.category = category;
        }

        public Writer title(final String title) {
            titleChanged = !ObjectUtils.equalAllowNull(title, category.title());
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, category.note());
            this.note = note;
            return this;
        }

        public void commit() throws CategoryArgResults.Exception {
            inspectInput();
            writeTitle();
            writeNote();
            save();
        }

        private void inspectInput() throws CategoryArgResults.Exception {
            argChecker().check();
        }

        private CategoryArgChecker argChecker() {
            final CategoryArgChecker argChecker = new CategoryArgChecker();
            if (titleChanged) {
                argChecker.title(title);
            }
            return argChecker;
        }

        private void writeTitle() {
            if (titleChanged) {
                category.title(title);
            }
        }

        private void writeNote() {
            if (noteChanged) {
                category.note(note);
            }
        }

        private void save() {
            if (titleChanged || noteChanged) {
                category.save();
            }
        }
    }
}
