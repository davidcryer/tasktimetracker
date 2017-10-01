package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.ObjectUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private String note;
    private final List<Session> sessionHistory = new LinkedList<>();
    private Session ongoingSession;

    public Task(final String title, final String note) {
        this(UUID.randomUUID(), title, note, null);
    }

    public Task(final UUID id, final String title, final String note, final Session ongoingSession) throws IllegalArgsException {
        ArgsInspector.inspect(
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        return id != null;
                    }
                }, "id cannot be null"),
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        return ongoingSession == null || ongoingSession.isOngoing();
                    }
                }, "ongoingSession cannot be finished")
        );
        this.id = id;
        this.title = title;
        this.note = note;
        this.ongoingSession = ongoingSession;
    }

    public void start() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        ongoingSession = new Session();
        ongoingSession.start();
    }

    public void stop() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        ongoingSession.stop();
        sessionHistory.add(ongoingSession);
        ongoingSession = null;
    }

    public boolean isOngoing() {
        return ongoingSession != null && ongoingSession.isOngoing();
    }

    public long getExpendedTime() {
        long expended = ongoingSession == null ? 0L : ongoingSession.duration();
        for (final Session session : sessionHistory) {
            expended += session.duration();
        }
        return expended;
    }

    public boolean deleteSession(final UUID sessionId) {
        for (final Iterator<Session> itr = sessionHistory.iterator(); itr.hasNext();) {
            if (itr.next().id().equals(sessionId)) {
                itr.remove();
                return true;
            }
        }
        return false;
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
            titleChanged = !ObjectUtils.equalAllowNull(title, this.title);
            this.title = title;
            return this;
        }

        public Writer note(final String note) {
            noteChanged = !ObjectUtils.equalAllowNull(note, this.note);
            this.note = note;
            return this;
        }

        public void commit() {
            writeTitle();
            writeNote();
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
}
