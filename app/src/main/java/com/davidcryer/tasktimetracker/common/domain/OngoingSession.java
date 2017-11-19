package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.DateUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalOngoingSessionArgsException;
import com.davidcryer.tasktimetracker.common.argvalidation.OngoingSessionArgsBuilder;

import java.util.Date;

public class OngoingSession implements Parcelable {
    private final static String ILLEGAL_START_MESSAGE = "start cannot be null";
    private final Date start;
    private Date stop;

    OngoingSession() {
        this(new Date(), null);
    }

    OngoingSession(final Date start, final Date stop) throws IllegalOngoingSessionArgsException {
        ArgsInspector.inspect(new OngoingSessionArgsBuilder().start(startArg(start)).args());
        this.start = start;
        this.stop = stop;
    }

    private static Arg startArg(final Date start) {
        return new Arg(start != null, ILLEGAL_START_MESSAGE);
    }

    FinishedSession stop() throws AlreadyStoppedException {
        if (isFinished()) {
            throw new AlreadyStoppedException();
        }
        stop = new Date();
        return new FinishedSession(start, stop);
    }

    public boolean isFinished() {
        return stop != null;
    }

    public long duration() {
        return DateUtils.difference(start, stop == null ? new Date() : stop);
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

    private OngoingSession(Parcel in) {
        long tmpStart = in.readLong();
        this.start = tmpStart == -1 ? null : new Date(tmpStart);
        long tmpStop = in.readLong();
        this.stop = tmpStop == -1 ? null : new Date(tmpStop);
    }

    public static final Creator<OngoingSession> CREATOR = new Creator<OngoingSession>() {
        @Override
        public OngoingSession createFromParcel(Parcel source) {
            return new OngoingSession(source);
        }

        @Override
        public OngoingSession[] newArray(int size) {
            return new OngoingSession[size];
        }
    };
}
