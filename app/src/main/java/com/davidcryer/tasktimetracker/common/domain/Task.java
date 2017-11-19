package com.davidcryer.tasktimetracker.common.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.ObjectUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalTaskArgsException;
import com.davidcryer.tasktimetracker.common.argvalidation.TaskArgsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Task implements Parcelable {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_TITLE_MESSAGE = "title cannot be null";
    private final static String ILLEGAL_ONGOING_SESSION_MESSAGE = "ongoing session cannot be finished";
    private final UUID id;
    private String title;
    private String note;
    private OngoingSession ongoingSession;
    private List<FinishedSession> finishedSessions;

    public Task(final String title, final String note) {
        this(UUID.randomUUID(), title, note, null);
    }

    public Task(final UUID id, final String title, final String note, final OngoingSession ongoingSession) throws IllegalTaskArgsException {
        this(id, title, note, ongoingSession, null);
    }

    private Task(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions) throws IllegalTaskArgsException {
        ArgsInspector.inspect(new TaskArgsBuilder().id(idArg(id)).title(titleArg(title)).ongoingSession(ongoingSessionArg(ongoingSession)).args());
        this.id = id;
        this.title = title;
        this.note = note;
        this.ongoingSession = ongoingSession;
        this.finishedSessions = finishedSessions;
    }

    private static Arg idArg(final UUID id) {
        return new Arg(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static Arg titleArg(final String title) {
        return new Arg(title != null, ILLEGAL_TITLE_MESSAGE);
    }

    private static Arg ongoingSessionArg(final OngoingSession ongoingSession) {
        return new Arg(ongoingSession == null || !ongoingSession.isFinished(), ILLEGAL_ONGOING_SESSION_MESSAGE);
    }

    void start() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        ongoingSession = new OngoingSession();
    }

    void stop() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        addFinishedSession(ongoingSession.stop());
        ongoingSession = null;
    }

    private void addFinishedSession(final FinishedSession session) {
        if (finishedSessions == null) {
            finishedSessions = new LinkedList<>();
        }
        finishedSessions.add(session);
    }

    public boolean isOngoing() {
        return ongoingSession != null;
    }

    public long expendedTime() {
        return ongoingSessionDuration() + finishedSessionsDuration();
    }

    private long ongoingSessionDuration() {
        return ongoingSession == null ? 0L : ongoingSession.duration();
    }

    private long finishedSessionsDuration() {
        long expended = 0L;
        if (finishedSessions != null) {
            for (final FinishedSession finishedSession : finishedSessions) {
                expended += finishedSession.duration();
            }
        }
        return expended;
    }

    public boolean deleteSession(final UUID sessionId) {
        if (finishedSessions != null) {
            for (final Iterator<FinishedSession> itr = finishedSessions.iterator(); itr.hasNext(); ) {
                if (itr.next().id().equals(sessionId)) {
                    itr.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public List<FinishedSession> finishedSessions() {
        return finishedSessions == null ? new ArrayList<FinishedSession>() : new ArrayList<>(finishedSessions);
    }

    public UUID id() {
        return id;
    }

    public String title() {
        return title;
    }

    private void title(final String title) {
        this.title = title;
    }

    public String note() {
        return note;
    }

    private void note(final String note) {
        this.note = note;
    }

    public Writer writer() {
        return new Writer(this);
    }

    public static class Writer {
        private final Task task;
        private String title;
        private boolean titleChanged;
        private String note;
        private boolean noteChanged;

        private Writer(Task task) {
            this.task = task;
        }

        public Writer title(final String title) {
            titleChanged = !ObjectUtils.equalAllowNull(title, task.title());
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, task.note());
            this.note = note;
            return this;
        }

        public void commit() throws IllegalTaskArgsException {
            inspectInput();
            writeTitle();
            writeNote();
        }

        private void inspectInput() throws IllegalTaskArgsException {
            ArgsInspector.inspect(args());
        }

        private IllegalTaskArgsException.Args args() {
            final TaskArgsBuilder builder = new TaskArgsBuilder();
            if (titleChanged) {
                builder.title(Task.titleArg(title));
            }
            return builder.args();
        }

        private void writeTitle() {
            if (titleChanged) {
                task.title(title);
            }
        }

        private void writeNote() {
            if (noteChanged) {
                task.note(note);
            }
        }
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

    private Task(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.title = in.readString();
        this.note = in.readString();
        this.ongoingSession = in.readParcelable(OngoingSession.class.getClassLoader());
        this.finishedSessions = in.createTypedArrayList(FinishedSession.CREATOR);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
