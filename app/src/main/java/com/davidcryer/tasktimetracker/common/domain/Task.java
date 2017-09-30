package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Task {
    private final List<Session> sessionHistory = new LinkedList<>();
    private Session ongoingSession;

    public Task() {
        this(null);
    }

    public Task(final Session ongoingSession) {
        ArgsInspector.inspect(
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        return ongoingSession == null || ongoingSession.isOngoing();
                    }
                }, "ongoingSession cannot be finished")
        );
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
}
