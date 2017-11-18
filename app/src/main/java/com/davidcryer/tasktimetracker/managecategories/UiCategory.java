package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

class UiCategory implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private boolean expanded;
    private final List<UiTask> tasks;

    public UiCategory(UUID id, String title, String note, boolean expanded, List<UiTask> tasks) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.expanded = expanded;
        this.tasks = tasks == null ? new ArrayList<UiTask>() : ListUtils.newList(tasks);
    }

    UUID getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getNote() {
        return note;
    }

    boolean isExpanded() {
        return expanded;
    }

    void expand() {
        setExpanded(true);
    }

    void shrink() {
        setExpanded(false);
    }

    private void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    int expandedTaskCount() {
        return isExpanded() ? tasks.size() : 0;
    }

    int taskCount() {
        return tasks.size();
    }

    UiTask task(int i) {
        return tasks.get(i);
    }

    int taskIndex(final UUID taskId) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(taskId)) {
                return i;
            }
        }
        return -1;
    }

    void addTask(final UiTask task) {
        tasks.add(task);
    }

    boolean removeTask(final UUID taskId) {
        for (final Iterator<UiTask> itr = tasks.iterator(); itr.hasNext();) {
            if (itr.next().getId().equals(taskId)) {
                itr.remove();
                return true;
            }
        }
        return false;
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
        dest.writeByte(this.expanded ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.tasks);
    }

    private UiCategory(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.expanded = in.readByte() != 0;
        this.tasks = in.createTypedArrayList(UiTask.CREATOR);
    }

    public static final Creator<UiCategory> CREATOR = new Creator<UiCategory>() {
        @Override
        public UiCategory createFromParcel(Parcel source) {
            return new UiCategory(source);
        }

        @Override
        public UiCategory[] newArray(int size) {
            return new UiCategory[size];
        }
    };
}
