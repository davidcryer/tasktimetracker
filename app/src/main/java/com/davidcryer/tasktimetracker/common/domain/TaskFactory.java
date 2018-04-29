package com.davidcryer.tasktimetracker.common.domain;

import java.util.List;
import java.util.UUID;

class TaskFactory {
    private final ActivatedTaskRegister activatedTaskRegister;

    TaskFactory(final ActivatedTaskRegister activatedTaskRegister) {
        this.activatedTaskRegister = activatedTaskRegister;
    }

    ObservedTask create(final String title, final String note) {
        return create(title, note, activatedTaskRegister);
    }

    ObservedTask inflate(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions) throws TaskArgResults.Exception {
        return inflate(id, title, note, ongoingSession, finishedSessions, activatedTaskRegister);
    }

    private static ObservedTask create(final String title, final String note, final ActivatedTaskRegister activatedTaskRegister) throws TaskArgResults.Exception {
        return new ObservedTask(UUID.randomUUID(), title, note, null, null, activatedTaskRegister);
    }

    private static ObservedTask inflate(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions, final ActivatedTaskRegister activatedTaskRegister) throws TaskArgResults.Exception {
        final ObservedTask task = new ObservedTask(id, title, note, ongoingSession, finishedSessions, activatedTaskRegister);
        if (task.isActive()) {
            activatedTaskRegister.setUp(task);
        }
        return task;
    }
}
