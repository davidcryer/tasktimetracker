package com.davidcryer.tasktimetracker.managestory;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class ManageStoryIntentModel implements Parcelable {
    private final UUID id;
    private final String title;
    private final String note;

    public ManageStoryIntentModel(UUID id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
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

    private ManageStoryIntentModel(Parcel in) {
        id = (UUID) in.readSerializable();
        title = in.readString();
        note = in.readString();
    }

    public static final Creator<ManageStoryIntentModel> CREATOR = new Creator<ManageStoryIntentModel>() {
        @Override
        public ManageStoryIntentModel createFromParcel(Parcel source) {
            return new ManageStoryIntentModel(source);
        }

        @Override
        public ManageStoryIntentModel[] newArray(int size) {
            return new ManageStoryIntentModel[size];
        }
    };
}
