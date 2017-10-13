package com.davidcryer.tasktimetracker.managetasks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
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

    private UiStory(final Parcel parcel) {
        id = (UUID) parcel.readSerializable();
        title = parcel.readString();
        note = parcel.readString();
        tasks = Arrays.asList((UiTask[]) parcel.readParcelableArray(UiTask.class.getClassLoader()));
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(id);
        parcel.writeString(title);
        parcel.writeString(note);
        parcel.writeParcelableArray(tasks.toArray(new UiTask[tasks.size()]), PARCELABLE_WRITE_RETURN_VALUE);
    }

    public final static Creator<UiStory> CREATOR = new Creator<UiStory>() {
        @Override
        public UiStory createFromParcel(Parcel parcel) {
            return new UiStory(parcel);
        }

        @Override
        public UiStory[] newArray(int i) {
            return new UiStory[i];
        }
    };
}
