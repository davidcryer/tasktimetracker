package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class UiStory implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private final List<UiTask> tasks;

    UiStory(UUID id, String title, String note, List<UiTask> tasks) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.tasks = tasks == null ? new ArrayList<UiTask>() : new ArrayList<>(tasks);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public List<UiTask> getTasks() {
        return new ArrayList<>(tasks);
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
