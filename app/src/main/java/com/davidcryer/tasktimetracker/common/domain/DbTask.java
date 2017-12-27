package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

class DbTask implements Serializable, Parcelable {
    private final UUID id;
    private String title;
    private String note;
    private DbOngoingSession ongoingSession;
    private List<DbFinishedSession> finishedSessions;

    DbTask(UUID id, String title, String note, DbOngoingSession ongoingSession, List<DbFinishedSession> finishedSessions) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.ongoingSession = ongoingSession;
        this.finishedSessions = finishedSessions;
    }

    Task toTask(final TaskFactory factory, final Task.OngoingStatusListener listener) {
        final Task task = factory.inflate(id, title, note, ongoingSession(), DbMapper.finishedSessions(finishedSessions));
        task.addOngoingStatusListener(listener);
        return task;
    }

    private OngoingSession ongoingSession() {
        return ongoingSession == null ? null : ongoingSession.toOngoingSession();
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
        dest.writeParcelable(this.ongoingSession, flags);
        dest.writeTypedList(this.finishedSessions);
    }

    private DbTask(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.ongoingSession = in.readParcelable(DbOngoingSession.class.getClassLoader());
        this.finishedSessions = in.createTypedArrayList(DbFinishedSession.CREATOR);
    }

    public static final Creator<DbTask> CREATOR = new Creator<DbTask>() {
        @Override
        public DbTask createFromParcel(Parcel source) {
            return new DbTask(source);
        }

        @Override
        public DbTask[] newArray(int size) {
            return new DbTask[size];
        }
    };
}
