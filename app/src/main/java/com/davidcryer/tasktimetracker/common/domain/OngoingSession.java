package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.DateUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalOngoingSessionArgsException;
import com.davidcryer.tasktimetracker.common.argvalidation.OngoingSessionArgsBuilder;

import java.util.Date;

public class OngoingSession {
    private final static String ILLEGAL_START_MESSAGE = "register cannot be null";
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

    DbOngoingSession toDbOngoingSession() {
        return new DbOngoingSession(start, stop);
    }
}
