package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

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

public class Category implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.title);
        dest.writeString(this.note);
        dest.writeTypedList(this.tasks);
    }

    private Category(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.tasks = in.createTypedArrayList(Task.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
