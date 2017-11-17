package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.Arg;
import com.davidcryer.tasktimetracker.common.argvalidation.ArgsInspector;
import com.davidcryer.tasktimetracker.common.argvalidation.FinishedSessionArgsBuilder;
import com.davidcryer.tasktimetracker.common.DateUtils;
import com.davidcryer.tasktimetracker.common.argvalidation.IllegalFinishedSessionArgsException;

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
}
