package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.Time;

import java.util.Date;

public class Contribution {
    private Date start;
    private Date finish;

    public void start() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        start = new Date();
    }

    public void stop() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        finish = new Date();
    }

    public boolean isOngoing() {
        return start != null && finish == null;
    }

    public long duration() {
        if (start == null) {
            return 0L;
        }
        final Date finish = this.finish == null ? new Date() : this.finish;
        return Time.difference(start, finish);
    }
}
