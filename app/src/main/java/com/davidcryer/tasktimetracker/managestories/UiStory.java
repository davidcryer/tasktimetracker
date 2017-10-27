package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.os.Parcelable;

import com.davidcryer.tasktimetracker.common.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UiStory implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private boolean expanded;
    private final List<UiTask> tasks;

    public UiStory(UUID id, String title, String note, boolean expanded, List<UiTask> tasks) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(id);
        dest.writeString(title);
        dest.writeString(note);
        dest.writeTypedList(tasks);
    }

    private UiStory(Parcel in) {
        id = (UUID) in.readSerializable();
        title = in.readString();
        note = in.readString();
        tasks = in.createTypedArrayList(UiTask.CREATOR);
    }

    public static final Creator<UiStory> CREATOR = new Creator<UiStory>() {
        @Override
        public UiStory createFromParcel(Parcel source) {
            return new UiStory(source);
        }

        @Override
        public UiStory[] newArray(int size) {
            return new UiStory[size];
        }
    };
}
