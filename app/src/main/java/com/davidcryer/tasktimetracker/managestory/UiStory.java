package com.davidcryer.tasktimetracker.managestory;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class UiStory implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;

    UiStory(UUID id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(id);
        dest.writeString(title);
        dest.writeString(note);
    }

    private UiStory(Parcel in) {
        id = (UUID) in.readSerializable();
        title = in.readString();
        note = in.readString();
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