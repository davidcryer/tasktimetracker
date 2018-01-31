package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

class TaskFactory {
    private final OngoingTaskRegister ongoingTaskRegister;

    TaskFactory(final OngoingTaskRegister ongoingTaskRegister) {
        this.ongoingTaskRegister = ongoingTaskRegister;
    }

    Task create(final String title, final String note) {
        return Task.create(title, note, ongoingTaskRegister);
    }

    Task inflate(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions) throws TaskArgResults.Exception {
        return Task.inflate(id, title, note, ongoingSession, finishedSessions, ongoingTaskRegister);
    }
}
