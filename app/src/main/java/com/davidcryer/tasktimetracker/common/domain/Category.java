package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalCategoryArgsException;
import com.davidcryer.tasktimetracker.common.ObjectUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.CategoryArgsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Category implements Task.OnChangeListener {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null or empty";
    private final CategoryStore categoryStore;
    private final TaskFactory taskFactory;
    private final UUID id;
    private String title;
    private String note;
    private List<Task> tasks;

    public Category(CategoryStore categoryStore, TaskFactory taskFactory, String title, String note) throws IllegalCategoryArgsException {
        this(categoryStore, taskFactory, UUID.randomUUID(), title, note, null);
    }

    Category(CategoryStore categoryStore, TaskFactory taskFactory, UUID id, String title, String note, List<Task> tasks) throws IllegalCategoryArgsException {
        ArgsInspector.inspect(new CategoryArgsBuilder().id(idArg(id)).title(titleArg(title)).args());
        this.categoryStore = categoryStore;
        this.taskFactory = taskFactory;
        this.id = id;
        this.title = title;
        this.note = note;
        this.tasks = tasks;
        tasks.forEach(task -> task.onChangeListener(this));
    }

    private static Arg idArg(final UUID id) {
        return new Arg(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static Arg titleArg(final String title) {
        return new Arg(title != null && !title.isEmpty(), ILLEGAL_TITLE_MESSAGE);
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

    public List<Task> tasks() {
        return tasks == null ? new ArrayList<>() : new ArrayList<>(tasks);
    }

    public Task newTask(final String title, final String note) {
        if (tasks == null) {
            tasks = new LinkedList<>();
        }
        final Task task = taskFactory.create(title, note);
        tasks.add(task);
        task.onChangeListener(this);
        save();
        return task;
    }

    public boolean deleteTask(final UUID taskId) {
        if (tasks != null) {
            for (final Iterator<Task> itr = tasks.iterator(); itr.hasNext(); ) {
                final Task task = itr.next();
                if (task.id().equals(taskId)) {
                    task.onChangeListener(null);
                    if (task.isOngoing()) {
                        task.stop();
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
        return new DbCategory(id, title, note, DbMapper.dbTasks(tasks));
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

        public void commit() throws IllegalCategoryArgsException {
            inspectInput();
            writeTitle();
            writeNote();
            save();
        }

        private void inspectInput() throws IllegalCategoryArgsException {
            ArgsInspector.inspect(args());
        }

        private IllegalCategoryArgsException.Args args() {
            final CategoryArgsBuilder argsBuilder = new CategoryArgsBuilder();
            if (titleChanged) {
                argsBuilder.title(Category.titleArg(title));
            }
            return argsBuilder.args();
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
