package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.argvalidation.FinishedSessionArgsBuilder;
import com.davidcryer.tasktimetracker.common.DateUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalFinishedSessionArgsException;

import java.util.Date;
import java.util.UUID;

public class FinishedSession implements Parcelable {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_START_MESSAGE = "start cannot be null";
    private final static String ILLEGAL_FINISH_MESSAGE = "finish cannot be null";
    private final static String ILLEGAL_TIMELINE_MESSAGE = "start cannot be later than finish";
    private final UUID id;
    private final Date start;
    private final Date finish;

    FinishedSession(final Date start, final Date finish) throws IllegalFinishedSessionArgsException {
        this(UUID.randomUUID(), start, finish);
    }

    FinishedSession(final UUID id, final Date start, final Date finish) throws IllegalFinishedSessionArgsException {
        ArgsInspector.inspect(new FinishedSessionArgsBuilder().id(idArg(id)).start(startArg(start)).finish(finishArg(finish)).timeline(timelineArg(start, finish)).args());
        this.id = id;
        this.start = start;
        this.finish = finish;
    }

    private static Arg idArg(final UUID id) {
        return new Arg(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static Arg startArg(final Date start) {
        return new Arg(start != null, ILLEGAL_START_MESSAGE);
    }

    private static Arg finishArg(final Date finish) {
        return new Arg(finish != null, ILLEGAL_FINISH_MESSAGE);
    }

    private static Arg timelineArg(final Date start, final Date finish) {
        return new Arg(start == null || finish == null || start.compareTo(finish) <= 0, ILLEGAL_TIMELINE_MESSAGE);
    }

    public UUID id() {
        return id;
    }

    public Date startTime() {
        return start == null ? null : new Date(start.getTime());
    }

    public Date finishTime() {
        return finish == null ? null : new Date(finish.getTime());
    }

    public long duration() {
        return DateUtils.difference(start, finish);
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

    private FinishedSession(Parcel in) {
        this.id = (UUID) in.readSerializable();
        long tmpStart = in.readLong();
        this.start = tmpStart == -1 ? null : new Date(tmpStart);
        long tmpFinish = in.readLong();
        this.finish = tmpFinish == -1 ? null : new Date(tmpFinish);
    }

    public static final Creator<FinishedSession> CREATOR = new Creator<FinishedSession>() {
        @Override
        public FinishedSession createFromParcel(Parcel source) {
            return new FinishedSession(source);
        }

        @Override
        public FinishedSession[] newArray(int size) {
            return new FinishedSession[size];
        }
    };
}
