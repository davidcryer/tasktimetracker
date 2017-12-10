package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

class DbFinishedSession implements Serializable, Parcelable {
    private final UUID id;
    private final Date start;
    private final Date finish;

    DbFinishedSession(UUID id, Date start, Date finish) {
        this.id = id;
        this.start = start;
        this.finish = finish;
    }

    FinishedSession toFinishedSession() {
        return new FinishedSession(id, start, finish);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeLong(this.start != null ? this.start.getTime() : -1);
        dest.writeLong(this.finish != null ? this.finish.getTime() : -1);
    }

    private DbFinishedSession(Parcel in) {
        this.id = (UUID) in.readSerializable();
        long tmpStart = in.readLong();
        this.start = tmpStart == -1 ? null : new Date(tmpStart);
        long tmpFinish = in.readLong();
        this.finish = tmpFinish == -1 ? null : new Date(tmpFinish);
    }

    public static final Creator<DbFinishedSession> CREATOR = new Creator<DbFinishedSession>() {
        @Override
        public DbFinishedSession createFromParcel(Parcel source) {
            return new DbFinishedSession(source);
        }

        @Override
        public DbFinishedSession[] newArray(int size) {
            return new DbFinishedSession[size];
        }
    };
}
