package com.davidcryer.tasktimetracker.managestories;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class UiTask implements Parcelable {
    private final UUID id;
    private final String title;

    UiTask(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    private UiTask(final Parcel parcel) {
        id = (UUID) parcel.readSerializable();
        title = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(id);
        parcel.writeString(title);
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
