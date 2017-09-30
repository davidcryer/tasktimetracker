package com.davidcryer.tasktimetracker.common.domain;

import java.util.LinkedList;
import java.util.List;

public class Task {
    private final List<Session> sessionHistory = new LinkedList<>();
    private Session ongoingSession;

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
}
