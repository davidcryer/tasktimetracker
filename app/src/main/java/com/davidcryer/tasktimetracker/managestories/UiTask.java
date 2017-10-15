package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class UiTask implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private final UUID storyId;

    UiTask(UUID id, String title, String note, UUID storyId) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.storyId = storyId;
    }

    private UiTask(final Parcel parcel) {
        id = (UUID) parcel.readSerializable();
        title = parcel.readString();
        note = parcel.readString();
        storyId = (UUID) parcel.readSerializable();
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

    public UUID getStoryId() {
        return storyId;
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
        parcel.writeSerializable(storyId);
    }

    public final static Creator<UiTask> CREATOR = new Creator<UiTask>() {
        @Override
        public UiTask createFromParcel(Parcel parcel) {
            return new UiTask(parcel);
        }

        @Override
        public UiTask[] newArray(int i) {
            return new UiTask[i];
        }
    };
}
