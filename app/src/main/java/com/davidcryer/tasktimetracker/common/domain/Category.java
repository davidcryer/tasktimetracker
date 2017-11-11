package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalCategoryArgsException;
import com.davidcryer.tasktimetracker.common.ObjectUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.CategoryArgsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Category {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null or empty";
    private final UUID id;
    private String title;
    private String note;
    private List<Task> tasks;

    public Category(String title, String note) throws IllegalCategoryArgsException {
        this(UUID.randomUUID(), title, note, null);
    }

    Category(UUID id, String title, String note, List<Task> tasks) throws IllegalCategoryArgsException {
        ArgsInspector.inspect(new CategoryArgsBuilder().id(idArg(id)).title(titleArg(title)).args());
        this.id = id;
        this.title = title;
        this.note = note;
        this.tasks = tasks;
    }

    private static ArgsInspector.Arg idArg(final UUID id) {
        return new ArgsInspector.Arg(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static ArgsInspector.Arg titleArg(final String title) {
        return new ArgsInspector.Arg(title != null && !title.isEmpty(), ILLEGAL_TITLE_MESSAGE);
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
        return tasks == null ? new ArrayList<Task>() : new ArrayList<>(tasks);
    }

    public boolean addTask(final Task task) {
        if (tasks == null) {
            tasks = new LinkedList<>();
        }
        return !tasks.contains(task) && tasks.add(task);
    }

    public boolean deleteTask(final UUID taskId) {
        if (tasks != null) {
            for (final Iterator<Task> itr = tasks.iterator(); itr.hasNext(); ) {
                if (itr.next().id().equals(taskId)) {
                    itr.remove();
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
    }
}
