package com.davidcryer.tasktimetracker.common.domain;

import com.davidcryer.tasktimetracker.common.argvalidation.IllegalTaskArgsException;

import java.util.List;
import java.util.UUID;

public class TaskFactory {
    private final OngoingTaskRegister ongoingTaskRegister;

    public TaskFactory(final OngoingTaskRegister ongoingTaskRegister) {
        this.ongoingTaskRegister = ongoingTaskRegister;
    }

    public Task create(final String title, final String note) {
        return new Task(title, note, ongoingTaskRegister);
    }

    Task create(final UUID id, final String title, final String note, final OngoingSession ongoingSession, final List<FinishedSession> finishedSessions) throws IllegalTaskArgsException {
        return new Task(id, title, note, ongoingSession, finishedSessions, ongoingTaskRegister);
    }
}
