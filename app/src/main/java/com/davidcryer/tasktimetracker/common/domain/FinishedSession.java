package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.DateUtils;

import java.util.Date;
import java.util.UUID;

public class FinishedSession {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_START_MESSAGE = "start cannot be null";
    private final static String ILLEGAL_FINISH_MESSAGE = "finish cannot be null";
    private final static String ILLEGAL_TIMELINE_MESSAGE = "start cannot be later than finish";
    private final UUID id;
    private final Date start;
    private final Date finish;

    FinishedSession(final Date start, final Date finish) throws IllegalArgsException {
        this(UUID.randomUUID(), start, finish);
    }

    FinishedSession(final UUID id, final Date start, final Date finish) {
        ArgsInspector.inspect(idCheck(id), startCheck(start), finishCheck(finish), timelineCheck(start, finish));
        this.id = id;
        this.start = start;
        this.finish = finish;
    }

    private static ArgsInspector.ArgCheck idCheck(final UUID id) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return id != null;
            }
        }, ILLEGAL_ID_MESSAGE);
    }

    private static ArgsInspector.ArgCheck startCheck(final Date start) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return start != null;
            }
        }, ILLEGAL_START_MESSAGE);
    }

    private static ArgsInspector.ArgCheck finishCheck(final Date finish) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return finish != null;
            }
        }, ILLEGAL_FINISH_MESSAGE);
    }

    private static ArgsInspector.ArgCheck timelineCheck(final Date start, final Date finish) {
        return ArgsInspector.check(new ArgsInspector.ArgCriteria() {
            @Override
            public boolean passed() {
                return start == null || finish == null || start.compareTo(finish) <= 0;
            }
        }, ILLEGAL_TIMELINE_MESSAGE);
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
}
