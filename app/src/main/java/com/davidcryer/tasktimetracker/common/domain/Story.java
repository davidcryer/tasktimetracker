package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Story {
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null";
    private final static String ILLEGAL_TASKS_MESSAGE = "tasks cannot be null";
    private String title;
    private String note;
    private final List<Task> tasks;

    public Story(String title, String note) {
        this(title, note, new LinkedList<Task>());
    }

    public Story(String title, String note, List<Task> tasks) {
        ArgsInspector.inspect(titleCheck(title), tasksCheck(tasks));
        this.title = title;
        this.note = note;
        this.tasks = tasks;
    }

    private static ArgsInspector.ArgCheck titleCheck(final String title) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return title != null;
            }
        }, ILLEGAL_TITLE_MESSAGE);
    }

    private static ArgsInspector.ArgCheck tasksCheck(final List<Task> tasks) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return tasks != null;
            }
        }, ILLEGAL_TASKS_MESSAGE);
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
        return new ArrayList<>(tasks);
    }

    public boolean addTask(final Task task) {
        return !tasks.contains(task) && tasks.add(task);
    }

    public boolean deleteTask(final UUID taskId) {
        for (final Iterator<Task> itr = tasks.iterator(); itr.hasNext();) {
            if (itr.next().id().equals(taskId)) {
                itr.remove();
                return true;
            }
        }
        return false;
    }

    public Writer writer() {
        return new Writer(this);
    }

    public static class Writer {
        private final Story story;
        private String title;
        private boolean titleChanged;
        private String note;
        private boolean noteChanged;

        private Writer(Story story) {
            this.story = story;
        }

        public Writer title(final String title) {
            titleChanged = !ObjectUtils.equalAllowNull(title, story.title());
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, story.note());
            this.note = note;
            return this;
        }

        public void commit() throws IllegalArgsException {
            inspectInput();
            writeTitle();
            writeNote();
        }

        private void inspectInput() throws IllegalArgsException {
            ArgsInspector.inspect(checks());
        }

        private ArgsInspector.ArgCheck[] checks() {
            final List<ArgsInspector.ArgCheck> checks = new ArrayList<>();
            if (titleChanged) {
                checks.add(Story.titleCheck(title));
            }
            return checks.toArray(new ArgsInspector.ArgCheck[checks.size()]);
        }

        private void writeTitle() {
            if (titleChanged) {
                story.title(title);
            }
        }

        private void writeNote() {
            if (noteChanged) {
                story.note(note);
            }
        }
    }
}
