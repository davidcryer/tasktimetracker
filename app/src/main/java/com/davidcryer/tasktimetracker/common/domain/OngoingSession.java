package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;
import com.davidcryer.tasktimetracker.common.DateUtils;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;

import java.util.Date;

public class OngoingSession {
    private final static String ILLEGAL_START_MESSAGE = "start cannot be null";
    private final Date start;
    private Date stop;

    OngoingSession() {
        this(new Date(), null);
    }

    OngoingSession(final Date start, final Date stop) throws IllegalArgsException {
        ArgsInspector.inspect(
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        return start != null;
                    }
                }, ILLEGAL_START_MESSAGE)
        );
        this.start = start;
        this.stop = stop;
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
}
