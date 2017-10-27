package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalStoryArgsException;
import com.davidcryer.tasktimetracker.common.ObjectUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.StoryArgsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Story {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null or empty";
    private final UUID id;
    private String title;
    private String note;
    private List<Task> tasks;

    public Story(String title, String note) throws IllegalStoryArgsException {
        this(UUID.randomUUID(), title, note, null);
    }

    Story(UUID id, String title, String note, List<Task> tasks) throws IllegalStoryArgsException {
        ArgsInspector.inspect(new StoryArgsBuilder().id(idArg(id)).title(titleArg(title)).args());
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

        public void commit() throws IllegalStoryArgsException {
            inspectInput();
            writeTitle();
            writeNote();
        }

        private void inspectInput() throws IllegalStoryArgsException {
            ArgsInspector.inspect(args());
        }

        private IllegalStoryArgsException.Args args() {
            final StoryArgsBuilder argsBuilder = new StoryArgsBuilder();
            if (titleChanged) {
                argsBuilder.title(Story.titleArg(title));
            }
            return argsBuilder.args();
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
