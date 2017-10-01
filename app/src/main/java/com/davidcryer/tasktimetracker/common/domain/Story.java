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

    public boolean addTask(final Task task) {
        return !tasks.contains(task) && tasks.add(task);
    }

    public List<Task> tasks() {
        return new ArrayList<>(tasks);
    }

    List<Task> mutableTasks() {
        return tasks;
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
        private List<Task> addedTasks;
        private boolean haveAddedTasks;
        private List<Task> deletedTasks;
        private boolean haveDeletedTasks;
        private List<UUID> deletedTaskIds;
        private boolean haveDeletedTasksById;

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

        public Writer add(final Task task) {
            ensureAddedTasks();
            if (!addedTasks.contains(task)) {
                addedTasks.add(task);
                haveAddedTasks = true;
            }
            removeFromDeletion(task);
            return this;
        }

        private void ensureAddedTasks() {
            if (addedTasks == null) {
                addedTasks = new LinkedList<>();
            }
        }

        private void removeFromDeletion(final Task task) {
            removeFromDeletedTasks(task);
            removeFromDeletedTaskIds(task.id());
        }

        private void removeFromDeletedTasks(final Task task) {
            if (haveDeletedTasks && deletedTasks.remove(task)) {
                if (deletedTasks.isEmpty()) {
                    haveDeletedTasks = false;
                }
            }
        }

        private void removeFromDeletedTaskIds(final UUID taskId) {
            if (haveDeletedTasksById && deletedTaskIds.remove(taskId)) {
                if (deletedTaskIds.isEmpty()) {
                    haveDeletedTasksById = false;
                }
            }
        }

        public Writer delete(final Task task) {
            ensureDeletedTasks();
            if (!deletedTasks.contains(task)) {
                deletedTasks.add(task);
                haveDeletedTasks = true;
            }
            removeFromAddition(task);
            removeFromDeletedTaskIds(task.id());
            return this;
        }

        private void ensureDeletedTasks() {
            if (deletedTasks == null) {
                deletedTasks = new LinkedList<>();
            }
        }

        private void removeFromAddition(final Task task) {
            if (haveAddedTasks && addedTasks.remove(task)) {
                if (addedTasks.isEmpty()) {
                    haveAddedTasks = false;
                }
            }
        }

        public Writer delete(final UUID taskId) {
            ensureDeletedTaskIds();
            if (!deletedTaskIds.contains(taskId)) {
                deletedTaskIds.add(taskId);
                haveDeletedTasksById = true;
            }
            removeFromAddition(taskId);
            removeFromDeletedTasks(taskId);
            return this;
        }

        private void ensureDeletedTaskIds() {
            if (deletedTaskIds == null) {
                deletedTaskIds = new LinkedList<>();
            }
        }

        private void removeFromAddition(final UUID taskId) {
            if (haveAddedTasks) {
                for (final Iterator<Task> itr = addedTasks.iterator(); itr.hasNext();) {
                    if (itr.next().id().equals(taskId)) {
                        itr.remove();
                        if (addedTasks.isEmpty()) {
                            haveAddedTasks = false;
                        }
                        return;
                    }
                }
            }
        }

        private void removeFromDeletedTasks(final UUID taskId) {
            if (haveDeletedTasks) {
                for (final Iterator<Task> itr = deletedTasks.iterator(); itr.hasNext();) {
                    if (itr.next().id().equals(taskId)) {
                        itr.remove();
                        if (deletedTasks.isEmpty()) {
                            haveDeletedTasks = false;
                        }
                        return;
                    }
                }
            }
        }

        public void commit() throws IllegalArgsException {
            inspectInput();
            writeTitle();
            writeNote();
            addTasks();
            deleteTasks();
            deleteTasksByIds();
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

        private void addTasks() {
            if (haveAddedTasks) {
                story.mutableTasks().addAll(addedTasks);
            }
        }

        private void deleteTasks() {
            if (haveDeletedTasks) {
                story.mutableTasks().removeAll(deletedTasks);
            }
        }

        private void deleteTasksByIds() {
            if (haveDeletedTasksById) {
                for (final Iterator<Task> itr = story.mutableTasks().iterator(); itr.hasNext();) {
                    if (deletedTaskIds.remove(itr.next().id())) {
                        itr.remove();
                    }
                }
            }
        }
    }
}
