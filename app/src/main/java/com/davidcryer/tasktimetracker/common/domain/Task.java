package com.davidcryer.tasktimetracker.common.domain;

import java.util.LinkedList;
import java.util.List;

public class Task {
    private Contribution ongoing;
    private final List<Contribution> contributionHistory = new LinkedList<>();

    public void start() throws AlreadyStartedException {
        if (isOngoing()) {
            throw new AlreadyStartedException();
        }
        ongoing = new Contribution();
        ongoing.start();
    }

    public void stop() throws AlreadyStoppedException {
        if (!isOngoing()) {
            throw new AlreadyStoppedException();
        }
        ongoing.stop();
        contributionHistory.add(ongoing);
        ongoing = null;
    }

    public boolean isOngoing() {
        return ongoing != null && ongoing.isOngoing();
    }

    public long getExpendedTime() {
        long expended = ongoing == null ? 0L : ongoing.duration();
        for (final Contribution contribution : contributionHistory) {
            expended += contribution.duration();
        }
        return expended;
    }
}
