package com.davidcryer.tasktimetracker.managetask;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class ManageTaskIntentModel implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;
    private final UUID storyId;

    public ManageTaskIntentModel(UUID id, String title, String note, UUID storyId) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.storyId = storyId;
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
        dest.writeSerializable(storyId);
    }

    private ManageTaskIntentModel(Parcel in) {
        id = (UUID) in.readSerializable();
        title = in.readString();
        note = in.readString();
        storyId = (UUID) in.readSerializable();
    }

    public static final Creator<ManageTaskIntentModel> CREATOR = new Creator<ManageTaskIntentModel>() {
        @Override
        public ManageTaskIntentModel createFromParcel(Parcel source) {
            return new ManageTaskIntentModel(source);
        }

        @Override
        public ManageTaskIntentModel[] newArray(int size) {
            return new ManageTaskIntentModel[size];
        }
    };
}
