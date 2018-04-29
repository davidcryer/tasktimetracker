package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.DateUtils;

import java.util.Date;

public class OngoingSession {
    private final Date start;
    private Date stop;

    OngoingSession() {
        this(new Date(), null);
    }

    OngoingSession(final Date start, final Date stop) throws OngoingSessionArgResults.Exception {
        new OngoingSessionArgChecker().start(start).check();
        this.start = start;
        this.stop = stop;
    }

    FinishedSession stop() throws AlreadyInactiveException {
        if (isFinished()) {
            throw new AlreadyInactiveException();
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

    DbOngoingSession toDbOngoingSession() {
        return new DbOngoingSession(start, stop);
    }
}
