package com.davidcryer.tasktimetracker.managestory;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

class UiTask implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private boolean isInEditMode;

    UiTask(UUID id, String title, String note, boolean isInEditMode) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.isInEditMode = isInEditMode;
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

    public boolean isInEditMode() {
        return isInEditMode;
    }

    public void setInEditMode(boolean inEditMode) {
        isInEditMode = inEditMode;
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
        dest.writeByte(this.isInEditMode ? (byte) 1 : (byte) 0);
    }

    protected UiTask(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.isInEditMode = in.readByte() != 0;
    }

    public static final Creator<UiTask> CREATOR = new Creator<UiTask>() {
        @Override
        public UiTask createFromParcel(Parcel source) {
            return new UiTask(source);
        }

        @Override
        public UiTask[] newArray(int size) {
            return new UiTask[size];
        }
    };
}
