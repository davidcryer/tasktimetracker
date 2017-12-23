package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

class DbCategory implements Serializable, Parcelable {
    private final UUID id;
    private String title;
    private String note;
    private List<DbTask> tasks;

    public DbCategory(UUID id, String title, String note, List<DbTask> tasks) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.tasks = tasks;
    }

    Category toCategory(final TaskFactory factory, final Task.OngoingStatusListener ongoingStatusListener) {
        return new Category(id, title, note, DbMapper.tasks(tasks, factory, ongoingStatusListener));
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
        dest.writeTypedList(this.tasks);
    }

    private DbCategory(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.tasks = in.createTypedArrayList(DbTask.CREATOR);
    }

    public static final Creator<DbCategory> CREATOR = new Creator<DbCategory>() {
        @Override
        public DbCategory createFromParcel(Parcel source) {
            return new DbCategory(source);
        }

        @Override
        public DbCategory[] newArray(int size) {
            return new DbCategory[size];
        }
    };
}