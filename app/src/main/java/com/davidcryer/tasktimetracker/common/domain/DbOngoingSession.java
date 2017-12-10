package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

class DbOngoingSession implements Serializable, Parcelable {
    private final Date start;
    private Date stop;

    DbOngoingSession(Date start, Date stop) {
        this.start = start;
        this.stop = stop;
    }

    OngoingSession toOngoingSession() {
        return new OngoingSession(start, stop);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.start != null ? this.start.getTime() : -1);
        dest.writeLong(this.stop != null ? this.stop.getTime() : -1);
    }

    private DbOngoingSession(Parcel in) {
        long tmpStart = in.readLong();
        this.start = tmpStart == -1 ? null : new Date(tmpStart);
        long tmpStop = in.readLong();
        this.stop = tmpStop == -1 ? null : new Date(tmpStop);
    }

    public static final Creator<DbOngoingSession> CREATOR = new Creator<DbOngoingSession>() {
        @Override
        public DbOngoingSession createFromParcel(Parcel source) {
            return new DbOngoingSession(source);
        }

        @Override
        public DbOngoingSession[] newArray(int size) {
            return new DbOngoingSession[size];
        }
    };
}
