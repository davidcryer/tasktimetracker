package com.davidcryer.tasktimetracker.managecategories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class UiTask implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;

    UiTask(UUID id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    private UiTask(final Parcel parcel) {
        id = (UUID) parcel.readSerializable();
        title = parcel.readString();
        note = parcel.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(id);
        parcel.writeString(title);
        parcel.writeString(note);
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
