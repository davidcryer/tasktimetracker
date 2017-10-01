package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.ArgsInspector;
import com.davidcryer.tasktimetracker.common.IllegalArgsException;
import com.davidcryer.tasktimetracker.common.DateUtils;

import java.util.Date;
import java.util.UUID;

public class Session {
    private final UUID id;
    private Date start;
    private Date finish;

    Session() {
        this(null, null);
    }

    Session(final Date start, final Date finish) throws IllegalArgsException {
        ArgsInspector.inspect(
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        return (start == null && finish == null) || (start != null && finish != null);
                    }
                }, "Either both start and finish must be null or both non-null"),
                ArgsInspector.check(new ArgsInspector.ArgCriteria() {
                    @Override
                    public boolean passed() {
                        if (start == null || finish == null) {
                            return true;
                        }
                        return start.getTime() <= finish.getTime();
                    }
                }, "start cannot be less than finish")
        );
        this.id = UUID.randomUUID();
        this.start = start;
        this.finish = finish;
    }

    void start() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        start = new Date();
    }

    void stop() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        finish = new Date();
    }

    boolean isOngoing() {
        return start != null && finish == null;
    }

    public Date startTime() {
        return start == null ? null : new Date(start.getTime());
    }

    public Date finishTime() {
        return finish == null ? null : new Date(finish.getTime());
    }

    public long duration() {
        if (start == null) {
            return 0L;
        }
        final Date finish = this.finish == null ? new Date() : this.finish;
        return DateUtils.difference(start, finish);
    }

    public UUID id() {
        return id;
    }
}
