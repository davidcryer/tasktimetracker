package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.argrules.Rule;
import com.davidcryer.tasktimetracker.common.DateUtils;

import java.util.Date;
import java.util.UUID;

public class FinishedSession {
    private final static String ILLEGAL_ID_MESSAGE = "id cannot be null";
    private final static String ILLEGAL_START_MESSAGE = "register cannot be null";
    private final static String ILLEGAL_FINISH_MESSAGE = "finish cannot be null";
    private final static String ILLEGAL_TIMELINE_MESSAGE = "register cannot be later than finish";
    private final UUID id;
    private final Date start;
    private final Date finish;

    FinishedSession(final Date start, final Date finish) throws FinishedSessionArgRules.Exception {
        this(UUID.randomUUID(), start, finish);
    }

    FinishedSession(final UUID id, final Date start, final Date finish) throws FinishedSessionArgRules.Exception {
        new FinishedSessionArgRulesBuilder().id(idArg(id)).start(startArg(start)).finish(finishArg(finish)).timeline(timelineArg(start, finish)).args().enforce();
        this.id = id;
        this.start = start;
        this.finish = finish;
    }

    private static Rule idArg(final UUID id) {
        return new Rule(id != null, ILLEGAL_ID_MESSAGE);
    }

    private static Rule startArg(final Date start) {
        return new Rule(start != null, ILLEGAL_START_MESSAGE);
    }

    private static Rule finishArg(final Date finish) {
        return new Rule(finish != null, ILLEGAL_FINISH_MESSAGE);
    }

    private static Rule timelineArg(final Date start, final Date finish) {
        return new Rule(start == null || finish == null || start.compareTo(finish) <= 0, ILLEGAL_TIMELINE_MESSAGE);
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
