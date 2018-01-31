package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.DateUtils;

import java.util.Date;
import java.util.UUID;

public class FinishedSession {
    private final UUID id;
    private final Date start;
    private final Date finish;

    FinishedSession(final Date start, final Date finish) throws FinishedSessionArgResults.Exception {
        this(UUID.randomUUID(), start, finish);
    }

    FinishedSession(final UUID id, final Date start, final Date finish) throws FinishedSessionArgResults.Exception {
        new FinishedSessionArgChecker().id(id).times(start, finish).check();
        this.id = id;
        this.start = start;
        this.finish = finish;
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

    DbFinishedSession toDbFinishedSession() {
        return new DbFinishedSession(id, start, finish);
    }
}
